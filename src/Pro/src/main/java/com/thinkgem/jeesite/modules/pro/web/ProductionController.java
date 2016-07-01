/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.Product.Bom;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.entity.Production;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.entity.ProductionPlan;
import com.thinkgem.jeesite.modules.pro.entity.Stock;
import com.thinkgem.jeesite.modules.pro.entity.page.ProductTreePage;
import com.thinkgem.jeesite.modules.pro.service.ProductService;
import com.thinkgem.jeesite.modules.pro.service.ProductTreeService;
import com.thinkgem.jeesite.modules.pro.service.ProductionDetailService;
import com.thinkgem.jeesite.modules.pro.service.ProductionPlanService;
import com.thinkgem.jeesite.modules.pro.service.ProductionService;
import com.thinkgem.jeesite.modules.pro.service.StockService;

/**
 * 生产管理Controller
 * @author Generate Tools
 * @version 2016-03-12
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/production")
public class ProductionController extends BaseController {

	@Autowired
	private ProductionService productionService;
	
	@Autowired
	private ProductionPlanService productionPlanService;
	
	@Autowired
	private ProductionDetailService productionDetailService;
	
	@Autowired
	private ProductTreeService productTreeService;
	
	@Autowired
	private ProductService productService;
	
	
	@ModelAttribute
	public Production get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return productionService.get(id);
		}else{
			return new Production();
		}
	}
	
	@RequiresPermissions("pro:production:view")
	@RequestMapping(value = {"list", ""})
	public String list(Production production, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			production.setCreateBy(user);
		}
        Page<Production> page = productionService.find(new Page<Production>(request, response), production); 
        model.addAttribute("page", page);
        
		return "modules/pro/productionList";
	}
	
	@RequiresPermissions("pro:production:view")
	@RequestMapping(value = "printView")
	public String printView(Production production, HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("production", production);
        
		//检查是否投产
		boolean isProduction = productionDetailService.findByProductionId(production.getId()).size()>0?true:false;
		
		
        int number = production.getNumber();//生产目标
        //获取需要投产的产品
        List<ProductTreePage> list = Lists.newArrayList();
        
        Product product = production.getProduct();
		if (product.getAssy() != Product.ASSY_SIMPLE) {
			List<ProductTree> roots = productTreeService
					.findParentsByProductId(product.getId());
			ProductTree root = roots.get(0);
			String id = IdGen.uuid();
			ProductTreePage treePage = new ProductTreePage(root.getId(), id, "", product.getName(), number);
			treePage.setProduct(product);
			list.add(treePage);
			List<ProductTree> childrens = productTreeService
					.findChildrensByProductId(product.getId());
			for (ProductTree c : childrens) {
				recursiveChildren(id, c, number, list);
			}
		} else {
			String id = IdGen.uuid();
			ProductTreePage treePage = new ProductTreePage(product.getId(), id,
					"", product.getName(), number);
			treePage.setProduct(product);
			list.add(treePage);
		}
       
        model.addAttribute("list", list);
        model.addAttribute("isProduction", isProduction);
        
		return "modules/pro/productionPrintView";
	}
	
	public void recursiveChildren(String parentId,ProductTree productTree,int number,List<ProductTreePage> list){
		ProductTreePage productTreePage = new ProductTreePage();
    	String id = IdGen.uuid();
		productTreePage.setTreeId(productTree.getId());
		productTreePage.setId(id);
		productTreePage.setParent(parentId);
		productTreePage.setName(productTree.getProduct().getName());
		productTreePage.setNumber(productTree.getNumber() * number);
		productTreePage.setProduct(productTree.getProduct());
		list.add(productTreePage);
		
		List<ProductTree> childrens = productTreeService.findChildrensByProductId(productTree.getProduct().getId());
		for(ProductTree c : childrens){
			recursiveChildren(id,c,productTree.getNumber() * number,list);
		}
	}
	
	
	@RequiresPermissions("pro:production:edit")
	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "print")
	public String print(String type,String id, String productTreeIds ,String numbers, String snps, String mods, Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ok", true);
		try {
			
			List<ProductionDetail> productionDetailList = Lists.newArrayList();
			
			if("detail".equals(type)){
				ProductionDetail detail = productionDetailService.get(id);
				
				if (null != detail.getProductTree() && detail.getProductTree().getParent().getAssy() == Product.ASSY_COM) {
					detail.setNextPart("组立");
				}else{
					detail.setNextPart("仓库");
				}
				
				int remainder = detail.getNumber()%detail.getProduction().getProduct().getSnpNum();
				detail.setRemainder(remainder);
				productionDetailList.add(detail);
			}else {
				ProductionDetail detail = productionDetailService.get(id);
				
				if (null != detail.getProductTree() && detail.getProductTree().getParent().getAssy() == Product.ASSY_COM) {
					detail.setNextPart("组立");
				}else{
					detail.setNextPart("仓库");
				}
				
				Product product = detail.getProduct();
				
				List<ProductTree> childrens = productTreeService.findChildrensByProductId(product.getId());
				StringBuffer elements = new StringBuffer();
				for (int i = 0; i < childrens.size(); i++) {
					ProductTree tree = childrens.get(i);
					elements.append((i + 1) + "，"+tree.getProduct().getName()).append("<br>");
				}
				detail.setData(elements.toString());
				
				productionDetailList.add(detail);
			}
			
			SimplePropertyPreFilter filter1 = new SimplePropertyPreFilter(ProductionDetail.class, "serialNum","production", "productTree", "number","remainder","data","product","nextPart");
			SimplePropertyPreFilter filter2 = new SimplePropertyPreFilter(ProductTree.class, "product");
			SimplePropertyPreFilter filter3 = new SimplePropertyPreFilter(Product.class, "serialNum", "name", "bomString", "machine");
			SimplePropertyPreFilter filter4 = new SimplePropertyPreFilter(Production.class, "priority","plan", "product");
			SimplePropertyPreFilter filter5 = new SimplePropertyPreFilter(ProductionPlan.class, "beginDate");
			
			String json =  JSONObject.toJSONString(productionDetailList, new SerializeFilter[]{filter1, filter2, filter3, filter4, filter5}, SerializerFeature.DisableCircularReferenceDetect);
			System.out.println(json);
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return "";
	}
	
	
	
	
	/**
	 * 整数补位
	 * @param num
	 * @param count
	 * @return
	 */
	private String toSeq(int num, int count){
		double power = Math.pow(10, count);
		if(num >= power){
			throw new RuntimeException();
		}
		String str = String.valueOf(num);
		int patch = count-str.length();
		StringBuilder builder = new StringBuilder(10);
		for(int i =0; i < patch; i++){
			builder.append("0");
		}
		builder.append(str);
		return builder.toString();
	}

	@RequiresPermissions("pro:production:view")
	@RequestMapping(value = "form")
	public String form(Production production, Model model) {
		model.addAttribute("production", production);
		
		List<ProductionPlan> productionPlanList = productionPlanService.findAll();
        model.addAttribute("productionPlanList", productionPlanList);
        
        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);
        
		return "modules/pro/productionForm";
	}

	@RequiresPermissions("pro:production:edit")
	@RequestMapping(value = "save")
	public String save(Production production, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, production)){
			return form(production, model);
		}
		//查询当前生产下是否已经进入生产
		List<ProductionDetail> productionDetailList = productionDetailService.findByProductionId(production.getId());
		if(productionDetailList.size()>0){
			addMessage(redirectAttributes, "保存失败！该生成计划已经进入生产状态！");
			return "redirect:"+Global.getAdminPath()+"/pro/production/?repage";
		}
		if(StringUtils.isBlank(production.getSerialNum())){
			ProductionPlan plan = productionPlanService.get(production.getPlan().getId());
			Product pro = productService.get(production.getProduct().getId());
			String serialNum = plan.getSerialNum()+ pro.getSerialNum();
			production.setSerialNum(serialNum);
		}
		productionService.save(production);
		addMessage(redirectAttributes, "保存生产管理成功");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "redirect:"+Global.getAdminPath()+"/pro/production/?repage";
	}
	
	@RequiresPermissions("pro:production:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		Production production = productionService.get(id);
		List<ProductionDetail> productionDetailList = productionDetailService.findByProductionId(production.getId());
		if(productionDetailList.size()>0){
			addMessage(redirectAttributes, "删除失败！该生成计划已经进入生产状态！");
			return "redirect:"+Global.getAdminPath()+"/pro/production/?repage";
		}
		productionService.delete(id);
		addMessage(redirectAttributes, "删除生产管理成功");
		return "redirect:"+Global.getAdminPath()+"/pro/production/?repage";
	}

}
