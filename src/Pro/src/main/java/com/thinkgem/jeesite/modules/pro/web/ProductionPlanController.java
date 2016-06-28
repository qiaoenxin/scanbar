/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.Production;
import com.thinkgem.jeesite.modules.pro.entity.ProductionPlan;
import com.thinkgem.jeesite.modules.pro.service.ProductService;
import com.thinkgem.jeesite.modules.pro.service.ProductionDetailService;
import com.thinkgem.jeesite.modules.pro.service.ProductionPlanService;
import com.thinkgem.jeesite.modules.pro.service.ProductionService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 生产计划Controller
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
	
	@ModelAttribute
	public ProductionPlan get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return productionPlanService.get(id);
		}else{
			return new ProductionPlan();
		}
	}
	
	@RequiresPermissions("pro:productionPlan:view")
	@RequestMapping(value = {"list", ""})
	public String list(Production production, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			production.setCreateBy(user);
		}
        Page<Production> page = productionService.find(new Page<Production>(request, response), production); 
        model.addAttribute("page", page);
		return "modules/pro/productionPlanList";
	}

	@RequiresPermissions("pro:productionPlan:view")
	@RequestMapping(value = "form")
	public String form(ProductionPlan productionPlan, Model model) {
		model.addAttribute("productionPlan", productionPlan);
		
		List<Product> productList = productService.findAllEndProduct();
        model.addAttribute("productList", productList);
        
        List<Production> productionList = productionService.findByPlanId(productionPlan.getId());
        model.addAttribute("productionList", productionList);
        
		return "modules/pro/productionPlanForm";
	}

	@RequiresPermissions("pro:productionPlan:edit")
	@RequestMapping(value = "save")
	public String save(ProductionPlan productionPlan,ProductionModel productionModel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productionPlan)){
			return form(productionPlan, model);
		}
		
		//查询当前生产下是否已经进入生产
		List<Production> productionList = productionService.findByPlanId(productionPlan.getId());
		for(Production p :productionList){
			boolean isProduction = productionDetailService.findByProductionId(p.getId()).size()>0?true:false;
			if(isProduction){
				addMessage(redirectAttributes, "该计划下的生产已经投产,不允许修改!");
				return "redirect:"+Global.getAdminPath()+"/pro/productionPlan/?repage";
			}
		}
		
		String productionPlanId = productionPlan.getId();
		
		if (StringUtils.isBlank(productionPlanId)) {
			productionPlanId = IdGen.uuid();
			productionPlan.setId(productionPlanId);
		}
		
		
		productionList = productionModel.getProductionList();
		
		for (Production production : productionList) {
			production.setPlan(productionPlan);
			Product product = productService.get(production.getProduct().getId());
			production.setSerialNum(productionPlan.getSerialNum()+product.getSerialNum());
		}
		
		productionPlanService.save(productionPlan);
		
		productionService.save(productionList);
		
		addMessage(redirectAttributes, "保存生产计划成功");
		return "redirect:"+Global.getAdminPath()+"/pro/productionPlan/?repage";
	}
	
	@RequiresPermissions("pro:productionPlan:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		//查询当前生产下是否已经进入生产
		List<Production> productionList = productionService.findByPlanId(id);
		for(Production p :productionList){
			boolean isProduction = productionDetailService.findByProductionId(p.getId()).size()>0?true:false;
			if(isProduction){
				addMessage(redirectAttributes, "该计划下的生产已经投产,不允许修改!");
				return "redirect:"+Global.getAdminPath()+"/pro/productionPlan/?repage";
			}
		}
				
		productionPlanService.delete(id);
		addMessage(redirectAttributes, "删除生产计划成功");
		return "redirect:"+Global.getAdminPath()+"/pro/productionPlan/?repage";
	}

}
