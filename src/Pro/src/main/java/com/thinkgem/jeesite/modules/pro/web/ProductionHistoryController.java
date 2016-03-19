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
import com.thinkgem.jeesite.modules.pro.entity.ProductionHistory;
import com.thinkgem.jeesite.modules.pro.service.ProductionHistoryService;

/**
 * 产品流历史Controller
 * @author Generate Tools
 * @version 2016-03-12
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/productionHistory")
public class ProductionHistoryController extends BaseController {

	@Autowired
	private ProductionHistoryService productionHistoryService;
	
	@ModelAttribute
	public ProductionHistory get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return productionHistoryService.get(id);
		}else{
			return new ProductionHistory();
		}
	}
	
	@RequiresPermissions("pro:productionHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductionHistory productionHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			productionHistory.setCreateBy(user);
		}
        Page<ProductionHistory> page = productionHistoryService.find(new Page<ProductionHistory>(request, response), productionHistory); 
        model.addAttribute("page", page);
		return "modules/pro/productionHistoryList";
	}

	@RequiresPermissions("pro:productFlowHistory:view")
	@RequestMapping(value = "form")
	public String form(ProductionHistory productionHistory, Model model) {
		model.addAttribute("productionHistory", productionHistory);
		return "pro/productionHistoryForm";
	}

	@RequiresPermissions("pro:productionHistory:edit")
	@RequestMapping(value = "save")
	public String save(ProductionHistory productionHistory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productionHistory)){
			return form(productionHistory, model);
		}
		productionHistoryService.save(productionHistory);
		addMessage(redirectAttributes, "保存产品流历史成功");
		return "redirect:"+Global.getAdminPath()+"/pro/productionHistory/?repage";
	}
	
	@RequiresPermissions("pro:productionHistory:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		productionHistoryService.delete(id);
		addMessage(redirectAttributes, "删除产品流历史成功");
		return "redirect:"+Global.getAdminPath()+"/pro/productionHistory/?repage";
	}

}
