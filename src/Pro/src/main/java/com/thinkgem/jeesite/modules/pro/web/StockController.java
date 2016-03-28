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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.pro.entity.Stock;
import com.thinkgem.jeesite.modules.pro.service.StockService;

/**
 * 库存管理Controller
 * @author Generate Tools
 * @version 2016-03-12
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/stock")
public class StockController extends BaseController {

	@Autowired
	private StockService stockService;
	
	@ModelAttribute
	public Stock get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return stockService.get(id);
		}else{
			return new Stock();
		}
	}
	
	@RequiresPermissions("pro:stock:view")
	@RequestMapping(value = {"list", ""})
	public String list(Stock stock, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			stock.setCreateBy(user);
		}
        Page<Stock> page = stockService.find(new Page<Stock>(request, response), stock); 
        model.addAttribute("page", page);
		return "modules/pro/stockList";
	}

	@RequiresPermissions("pro:stock:view")
	@RequestMapping(value = "form")
	public String form(Stock stock, Model model) {
		model.addAttribute("stock", stock);
		return "modules/pro/stockForm";
	}

	@RequiresPermissions("pro:stock:edit")
	@RequestMapping(value = "save")
	public String save(Stock stock, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, stock)){
			return form(stock, model);
		}
		stockService.save(stock);
		addMessage(redirectAttributes, "保存库存管理成功");
		return "redirect:"+Global.getAdminPath()+"/pro/stock/?repage";
	}
	
	@RequiresPermissions("pro:stock:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		stockService.delete(id);
		addMessage(redirectAttributes, "删除库存管理成功");
		return "redirect:"+Global.getAdminPath()+"/pro/stock/?repage";
	}
	
	@RequiresPermissions("pro:stock:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Stock stock, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "库存数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx"; 
    		Page<Stock> page = stockService.find(new Page<Stock>(),new Stock()); 
    		new ExportExcel("库存数据", Stock.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出库存失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/pro/stock/?repage";
    }

}
