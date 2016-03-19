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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.StockHistory;
import com.thinkgem.jeesite.modules.pro.service.ProductService;
import com.thinkgem.jeesite.modules.pro.service.StockHistoryService;

/**
 * 出入库记录Controller
 * @author Generate Tools
 * @version 2016-03-12
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/stockHistory")
public class StockHistoryController extends BaseController {

	@Autowired
	private StockHistoryService stockHistoryService;
	
	@Autowired
	private ProductService productService;
	
	@ModelAttribute
	public StockHistory get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return stockHistoryService.get(id);
		}else{
			return new StockHistory();
		}
	}
	
	@RequiresPermissions("pro:stockHistory:view")
	@RequestMapping(value = {"list", ""})
	public String list(StockHistory stockHistory, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			stockHistory.setCreateBy(user);
		}
        Page<StockHistory> page = stockHistoryService.find(new Page<StockHistory>(request, response), stockHistory); 
        model.addAttribute("page", page);
        
		return "modules/pro/stockHistoryList";
	}

	@RequiresPermissions("pro:stockHistory:view")
	@RequestMapping(value = "form")
	public String form(StockHistory stockHistory, Model model) {
		model.addAttribute("stockHistory", stockHistory);
		
		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);
		
		return "modules/pro/stockHistoryForm";
	}

	@RequiresPermissions("pro:stockHistory:edit")
	@RequestMapping(value = "save")
	public String save(StockHistory stockHistory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, stockHistory)){
			return form(stockHistory, model);
		}
		try {
			stockHistoryService.save(stockHistory);
			addMessage(redirectAttributes, "保存出入库记录成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
			addMessage(redirectAttributes, "保存出入库记录失败");
		}
		
		return "redirect:"+Global.getAdminPath()+"/pro/stockHistory/?repage";
	}
	
	@RequiresPermissions("pro:stockHistory:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		stockHistoryService.delete(id);
		addMessage(redirectAttributes, "删除出入库记录成功");
		return "redirect:"+Global.getAdminPath()+"/pro/stockHistory/?repage";
	}

}
