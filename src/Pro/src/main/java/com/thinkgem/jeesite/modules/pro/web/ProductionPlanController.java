/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.web;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.print.attribute.HashPrintJobAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.drew.lang.StringUtil;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.Production;
import com.thinkgem.jeesite.modules.pro.entity.ProductionPlan;
import com.thinkgem.jeesite.modules.pro.entity.ProductionPlanExcel;
import com.thinkgem.jeesite.modules.pro.entity.Stock;
import com.thinkgem.jeesite.modules.pro.service.ProductService;
import com.thinkgem.jeesite.modules.pro.service.ProductionDetailService;
import com.thinkgem.jeesite.modules.pro.service.ProductionPlanService;
import com.thinkgem.jeesite.modules.pro.service.ProductionService;
import com.thinkgem.jeesite.modules.pro.service.StockService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 生产计划Controller
 * 
 * @author Generate Tools
 * @version 2016-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/productionPlan")
public class ProductionPlanController extends BaseController {

	@Autowired
	private ProductionPlanService productionPlanService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductionService productionService;

	@Autowired
	private ProductionDetailService productionDetailService;

	@Autowired
	private StockService stockService;

	@ModelAttribute
	public ProductionPlan get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return productionPlanService.get(id);
		} else {
			return new ProductionPlan();
		}
	}

	@RequiresPermissions("pro:productionPlan:view")
	@RequestMapping(value = { "list", "" })
	public String list(Production production, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			production.setCreateBy(user);
		}
		Page<Production> page = productionService.find(new Page<Production>(request, response), production);
		model.addAttribute("page", page);
		return "modules/pro/productionPlanList";
	}

	@RequiresPermissions("pro:productionPlan:view")
	@RequestMapping(value = "form")
	public String form(ProductionPlan productionPlan, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("productionPlan", productionPlan);

		// 查询当前生产下是否已经进入生产
		List<Production> list = productionService.findByPlanId(productionPlan.getId());
		for (Production p : list) {
			boolean isProduction = productionDetailService.findByProductionId(p.getId()).size() > 0 ? true : false;
			if (isProduction) {
				addMessage(redirectAttributes, "该计划下的生产已经投产,不允许修改!");
				return "redirect:" + Global.getAdminPath() + "/pro/productionPlan/?repage";
			}
		}

		// 所有种类为成品或者半成品的产品
		List<Product> productList = productService.findAllEndProduct();
		model.addAttribute("productList", productList);

		// 产品库存
		List<Stock> stocks = stockService.findAll();
		Map<String, Integer> stockMap = new HashMap<String, Integer>();
		for (Stock stock : stocks) {
			stockMap.put(stock.getProduct().getId(), stock.getNumber());
		}
		model.addAttribute("stock", stockMap);

		// 生产指令下的所有生产
		List<Production> productionList = productionService.findByPlanId(productionPlan.getId());
		model.addAttribute("productionList", productionList);

		return "modules/pro/productionPlanForm";
	}

	@RequiresPermissions("pro:productionPlan:edit")
	@RequestMapping(value = "save")
	public String save(ProductionPlan productionPlan, ProductionModel productionModel, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productionPlan)) {
			return form(productionPlan, model, redirectAttributes);
		}

		// 查询当前生产下是否已经进入生产
		List<Production> productionList = productionService.findByPlanId(productionPlan.getId());
		for (Production p : productionList) {
			boolean isProduction = productionDetailService.findByProductionId(p.getId()).size() > 0 ? true : false;
			if (isProduction) {
				addMessage(redirectAttributes, "该计划下的生产已经投产,不允许修改!");
				return "redirect:" + Global.getAdminPath() + "/pro/productionPlan/?repage";
			}
		}

		// 页面提交的production集合
		productionList = productionModel.getProductionList();
		if (CollectionUtils.isEmpty(productionList)) {
			addMessage(redirectAttributes, "保存失败,生产指令下的产品不得为空!");
			return "redirect:" + Global.getAdminPath() + "/pro/productionPlan/?repage";
		}

		String productionPlanId = productionPlan.getId();
		String planSerial = productionPlan.getSerialNum();
		if (StringUtils.isBlank(productionPlanId)) {
			long count = productionPlanService.countBySerialNum(planSerial);
			if (count > 0) {
				addMessage(redirectAttributes, "保存失败,存在相同的批次!");
				return "redirect:" + Global.getAdminPath() + "/pro/productionPlan/?repage";
			}

			productionPlanId = IdGen.uuid();
			productionPlan.setId(productionPlanId);
		}

		Set<String> productIds = new HashSet<String>();
		for (Production production : productionList) {
			production.setPlan(productionPlan);
			Product product = productService.get(production.getProduct().getId());
			String productSerial = product.getSerialNum();

			if (StringUtils.isBlank(planSerial) || StringUtils.isBlank(productSerial)) {
				addMessage(redirectAttributes, "保存失败,指令批次或产品编号不得为空!");
				return "redirect:" + Global.getAdminPath() + "/pro/productionPlan/?repage";
			}

			productIds.add(product.getId());
			production.setSerialNum(productionPlan.getSerialNum() + product.getSerialNum());
		}

		if (productionList.size() != productIds.size()) {
			addMessage(redirectAttributes, "保存失败,产品不得重复!");
			return "redirect:" + Global.getAdminPath() + "/pro/productionPlan/?repage";
		}

		productionPlanService.save(productionPlan);
		productionService.save(productionList);

		productionService.deleteByFlag();

		addMessage(redirectAttributes, "保存生产计划成功");
		return "redirect:" + Global.getAdminPath() + "/pro/productionPlan/?repage";
	}

	@RequiresPermissions("pro:productionPlan:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		// 查询当前生产下是否已经进入生产
		List<Production> productionList = productionService.findByPlanId(id);
		for (Production p : productionList) {
			boolean isProduction = productionDetailService.findByProductionId(p.getId()).size() > 0 ? true : false;
			if (isProduction) {
				addMessage(redirectAttributes, "该计划下的生产已经投产,不允许修改!");
				return "redirect:" + Global.getAdminPath() + "/pro/productionPlan/?repage";
			}
		}
		productionService.deleteByPlan(id);
		productionPlanService.delete(id);
		addMessage(redirectAttributes, "删除生产计划成功");
		return "redirect:" + Global.getAdminPath() + "/pro/productionPlan/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "checkSerial")
	public String checkSerial(String serialNum, String id) {
		return String.valueOf(isSerialUnique(serialNum, id));
	}
	
	private boolean isSerialUnique(String serialNum, String id){
		long count = productionPlanService.countBySerialNum(serialNum);

		if (StringUtils.isBlank(id)) {
			return count < 1;
		} else {
			return count < 2;
		}
	}

	@RequiresPermissions("pro:productionPlan:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProductionPlanExcel> list = ei.getDataList(ProductionPlanExcel.class);
			
			Set<String> serialNums = new HashSet<String>();
			Set<String> endDates =  new HashSet<String>();
			String serialNum = null;
			String endDate =  null;
			List<Production> productionList = Lists.newArrayList();
			Map<String, Integer> productNumber = new HashMap<String, Integer>();
			
			for (ProductionPlanExcel planExcel : list) {
				if (StringUtils.isNotBlank(planExcel.getSerialNum())) {
					serialNum = planExcel.getSerialNum();
					serialNums.add(serialNum);
				}
				if (StringUtils.isNotBlank(planExcel.getEndDate())) {
					endDate = planExcel.getEndDate();
					endDates.add(endDate);
				}
				productNumber.put(planExcel.getProductName(), planExcel.getNumber());
			}
			
			if (serialNums.size() != 1) {
				addMessage(redirectAttributes, "导入失败！失败信息：批次不一致");
				return "redirect:"+Global.getAdminPath()+"/pro/productionPlan/?repage";
			}
			if (endDates.size() != 1) {
				addMessage(redirectAttributes, "导入失败！失败信息：日期不一致");
				return "redirect:"+Global.getAdminPath()+"/pro/productionPlan/?repage";
			}
			if (productNumber.keySet().size() != list.size()) {
				addMessage(redirectAttributes, "导入失败！失败信息：产品不能重复");
				return "redirect:"+Global.getAdminPath()+"/pro/productionPlan/?repage";
			}
			if (!isSerialUnique(serialNum, null)) {
				addMessage(redirectAttributes, "导入失败！失败信息：批次与现有记录冲突");
				return "redirect:"+Global.getAdminPath()+"/pro/productionPlan/?repage";
			}
			
			ProductionPlan plan = new ProductionPlan();
			plan.setId(IdGen.uuid());
			plan.setSerialNum(serialNum);
			plan.setEndDate(DateUtils.parseDate(endDate, new String[]{"yyyyMMdd"}));

			for (String productName : productNumber.keySet()) {
				List<Product> products = productService.findByName(productName);
				
				if (products.size() != 1) {
					throw new RuntimeException("产品不存在，或者不唯一");
				}
				
				Production production = new Production();
				production.setPlan(plan);
				production.setProduct(products.get(0));
				production.setSerialNum(serialNum + products.get(0).getSerialNum());
				production.setNumber(productNumber.get(productName));
				
				productionList.add(production);
			}
			
			productionPlanService.save(plan);
			productionService.save(productionList);
			
			addMessage(redirectAttributes, "导入成功 !");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/pro/productionPlan/?repage";
    }
	
	@RequiresPermissions("pro:productionPlan:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "生产指令导入模板.xlsx";
    		List<ProductionPlanExcel> list = Lists.newArrayList();
    		new ExportExcel("生产指令(*请将单元格中数字设为文本格式)", ProductionPlanExcel.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/pro/productionPlan/?repage";
    }	
	
}
