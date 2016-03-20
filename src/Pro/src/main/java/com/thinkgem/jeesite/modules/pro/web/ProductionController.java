/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;
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
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.pro.entity.Product;
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
        
        Product product = production.getPlan().getProduct();
        List<ProductTree> productTreeList =  productTreeService.findChildrensByProductId(product.getId());;
       
        if(productTreeList.size() == 0){
        	String id = IdGen.uuid();
            list.add(new ProductTreePage(product.getId(),id,"",product.getSerialNum(),number));
        }else{
            for(ProductTree productTree : productTreeList){
            	
            	ProductTreePage productTreePage = new ProductTreePage();
            	String id = IdGen.uuid();
    			productTreePage.setTreeId(productTree.getId());
    			productTreePage.setId(id);
    			productTreePage.setParent("");
    			productTreePage.setName(productTree.getProduct().getSerialNum());
    			
    			productTreePage.setNumber(productTree.getNumber()*number);
    			
    			list.add(productTreePage);
    			List<ProductTree> childrens = productTreeService.findChildrensByProductId(productTree.getProduct().getId());
    			for(ProductTree c : childrens){
    				recursiveChildren(id,c,productTree.getNumber(),list);
    			}
    		}
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
		productTreePage.setName(productTree.getProduct().getSerialNum());
		productTreePage.setNumber(productTree.getNumber()*number);
		list.add(productTreePage);
		
		List<ProductTree> childrens = productTreeService.findChildrensByProductId(productTree.getProduct().getId());
		for(ProductTree c : childrens){
			recursiveChildren(id,c,productTree.getNumber(),list);
		}
	}
	
	
	@RequiresPermissions("pro:production:edit")
	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "print")
	public Map<String, Object> print(String productionId, String productTreeIds ,String numbers,Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ok", true);
		try {
			
			List <ProductionDetail> list = productionDetailService.findByProductionId(productionId);
			//检查是否投产
			if(list.size()>0){
				return map;
			}
			
			List<ProductionDetail> productionDetailList = Lists.newArrayList();
			List<Integer> useNumbers = Lists.newArrayList();
			
			Production production = productionService.get(productionId);
			String[] productTreeAry = productTreeIds.split(",");
			String[] numberAry = numbers.split(",");
			int index = 0;
			for(String productTreeId : productTreeAry){
				ProductTree productTree = productTreeService.get(productTreeId);
				int number = StringUtils.toInteger(numberAry[index]);
				
				index += 1;
				
				ProductionDetail detail = new ProductionDetail();
				detail.setProduction(production);
				detail.setSerialNum(production.getSerialNum()+RandomUtils.nextInt(10000));
				detail.setProductTree(productTree);
				detail.setNumber(number);
				productionDetailList.add(detail);
				
				useNumbers.add(number);
			}
			productionDetailService.save(productionDetailList);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
			map.put("ok", false);
			map.put("error", "添加失败！");
		}
		return map;
	}

	@RequiresPermissions("pro:production:view")
	@RequestMapping(value = "form")
	public String form(Production production, Model model) {
		model.addAttribute("production", production);
		
		List<ProductionPlan> productionPlanList = productionPlanService.findAll();
        model.addAttribute("productionPlanList", productionPlanList);
        
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
		
		//判断增加的生产总数是否超出计划范围
		int number = production.getNumber();
		ProductionPlan plan = productionPlanService.get(production.getPlan().getId());
		int planNumber = plan.getNumber();
		List<Production> productions = productionService.findByPlanId(plan.getId());
		for(Production production2 : productions){
			number+=production2.getNumber();
		}
		if(number>planNumber){
			addMessage(redirectAttributes, "超出计划范围!");
			return "redirect:"+Global.getAdminPath()+"/pro/production/?repage";
		}
		
		productionService.save(production);
		addMessage(redirectAttributes, "保存生产管理成功");
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
