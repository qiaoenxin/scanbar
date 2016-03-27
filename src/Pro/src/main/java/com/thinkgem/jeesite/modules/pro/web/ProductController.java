/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
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
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.service.ProductService;

/**
 * 产品管理Controller
 * @author Generate Tools
 * @version 2016-03-12
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;
	
	@ModelAttribute
	public Product get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return productService.get(id);
		}else{
			return new Product();
		}
	}
	
	@RequiresPermissions("pro:product:view")
	@RequestMapping(value = {"list", ""})
	public String list(Product product, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			product.setCreateBy(user);
		}
        Page<Product> page = productService.find(new Page<Product>(request, response), product); 
        model.addAttribute("page", page);
		return "modules/pro/productList";
	}

	@RequiresPermissions("pro:product:view")
	@RequestMapping(value = "form")
	public String form(Product product, Model model) {
		model.addAttribute("product", product);
		return "modules/pro/productForm";
	}
	
	@RequiresPermissions("pro:product:edit")
	@RequestMapping(value = "flow")
	public String flow(Product product, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("product", product);
		
		JSONArray flows = new JSONArray();
		try {
			if(StringUtils.isNotBlank(product.getFlow())){
				flows = JSONObject.parseArray(product.getFlow());
				for(int i = 0;i<flows.size();i++){
					JSONObject flow = flows.getJSONObject(i);
					String id = flow.getString("id");
					String label = DictUtils.getDictLabel(id, "flow_type", "");
					flow.put("label", label);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("flowList", flows);
		
		return "modules/pro/productFlow";
	}
	
	@RequiresPermissions("pro:product:edit")
	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "saveFlow")
	public Map<String, Object> saveFlow(String id, String flows ,Model model, RedirectAttributes redirectAttributes) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ok", true);
		try {
			Product product = productService.get(id);
			product.setFlow(flows);
			productService.save(product);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
			map.put("ok", false);
			map.put("error", "添加失败！");
		}
		return map;
	}

	@RequiresPermissions("pro:product:edit")
	@RequestMapping(value = "save")
	public String save(Product product, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, product)){
			return form(product, model);
		}
		productService.save(product);
		addMessage(redirectAttributes, "保存产品管理成功");
		return "redirect:"+Global.getAdminPath()+"/pro/product/?repage";
	}
	
	@RequiresPermissions("pro:product:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		productService.delete(id);
		addMessage(redirectAttributes, "删除产品管理成功");
		return "redirect:"+Global.getAdminPath()+"/pro/product/?repage";
	}

}
