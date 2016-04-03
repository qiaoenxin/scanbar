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
import com.thinkgem.jeesite.modules.pro.entity.StockBills;
import com.thinkgem.jeesite.modules.pro.service.StockBillsService;

/**
 * 扎帐Controller
 * @author Generate Tools
 * @version 2016-04-03
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/stockBills")
public class StockBillsController extends BaseController {

	@Autowired
	private StockBillsService stockBillsService;
	
	@ModelAttribute
	public StockBills get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return stockBillsService.get(id);
		}else{
			return new StockBills();
		}
	}
	
	@RequiresPermissions("pro:stockBills:view")
	@RequestMapping(value = {"list", ""})
	public String list(StockBills stockBills, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			stockBills.setCreateBy(user);
		}
        Page<StockBills> page = stockBillsService.find(new Page<StockBills>(request, response), stockBills); 
        model.addAttribute("page", page);
		return "modules/pro/stockBillsList";
	}

	@RequiresPermissions("pro:stockBills:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		stockBillsService.delete(id);
		addMessage(redirectAttributes, "删除扎帐成功");
		return "redirect:"+Global.getAdminPath()+"/pro/stockBills/?repage";
	}

}
