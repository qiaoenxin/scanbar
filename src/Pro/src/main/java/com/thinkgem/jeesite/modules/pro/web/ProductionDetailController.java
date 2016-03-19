/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.web;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.service.ProductionDetailService;

/**
 * 生产详情Controller
 * @author Generate Tools
 * @version 2016-03-12
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/productionDetail")
public class ProductionDetailController extends BaseController {

	@Autowired
	private ProductionDetailService productionDetailService;
	
	@ModelAttribute
	public ProductionDetail get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return productionDetailService.get(id);
		}else{
			return new ProductionDetail();
		}
	}
	
	@RequiresPermissions("pro:productionDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductionDetail productionDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			productionDetail.setCreateBy(user);
		}
        Page<ProductionDetail> page = productionDetailService.find(new Page<ProductionDetail>(request, response), productionDetail); 
        model.addAttribute("page", page);
		return "modules/pro/productionDetailList";
	}

	@RequiresPermissions("pro:productionDetail:view")
	@RequestMapping(value = "form")
	public String form(ProductionDetail productionDetail, Model model) {
		model.addAttribute("productionDetail", productionDetail);
		return "modules/pro/productionDetailForm";
	}

	@RequiresPermissions("pro:productionDetail:edit")
	@RequestMapping(value = "save")
	public String save(ProductionDetail productionDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productionDetail)){
			return form(productionDetail, model);
		}
		productionDetailService.save(productionDetail);
		addMessage(redirectAttributes, "保存生产详情成功");
		return "redirect:"+Global.getAdminPath()+"/pro/productionDetail/?repage";
	}
	
	@RequiresPermissions("pro:productionDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		productionDetailService.delete(id);
		addMessage(redirectAttributes, "删除生产详情成功");
		return "redirect:"+Global.getAdminPath()+"/pro/productionDetail/?repage";
	}

}
