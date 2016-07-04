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
import com.thinkgem.jeesite.modules.pro.entity.Loss;
import com.thinkgem.jeesite.modules.pro.service.LossService;

/**
 * 损失Controller
 * @author Generate Tools
 * @version 2016-07-03
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/loss")
public class LossController extends BaseController {

	@Autowired
	private LossService lossService;
	
	@ModelAttribute
	public Loss get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return lossService.get(id);
		}else{
			return new Loss();
		}
	}
	
	@RequiresPermissions("pro:loss:view")
	@RequestMapping(value = {"list", ""})
	public String list(Loss loss, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			loss.setCreateBy(user);
		}
        Page<Loss> page = lossService.find(new Page<Loss>(request, response), loss); 
        model.addAttribute("page", page);
		return "pro/lossList";
	}

	@RequiresPermissions("pro:loss:view")
	@RequestMapping(value = "form")
	public String form(Loss loss, Model model) {
		model.addAttribute("loss", loss);
		return "pro/lossForm";
	}

	@RequiresPermissions("pro:loss:edit")
	@RequestMapping(value = "save")
	public String save(Loss loss, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, loss)){
			return form(loss, model);
		}
		lossService.save(loss);
		return "redirect:"+Global.getAdminPath()+"/pro/loss/?repage";
	}
	
	@RequiresPermissions("pro:loss:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		lossService.delete(id);
		addMessage(redirectAttributes, "删除损失成功");
		return "redirect:"+Global.getAdminPath()+"/pro/loss/?repage";
	}

}
