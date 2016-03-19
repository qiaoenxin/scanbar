/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.web;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.service.ProductService;
import com.thinkgem.jeesite.modules.pro.service.ProductTreeService;

/**
 * 产品流管理Controller
 * @author Generate Tools
 * @version 2016-03-12
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/productTree")
public class ProductTreeController extends BaseController {

	@Autowired
	private ProductTreeService productTreeService;
	
	@Autowired
	private ProductService productService;
	
	@ModelAttribute
	public ProductTree get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return productTreeService.get(id);
		}else{
			return new ProductTree();
		}
	}
	
	@RequiresPermissions("pro:productTree:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductTree productTree, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			productTree.setCreateBy(user);
		}
        Page<ProductTree> page = productTreeService.find(new Page<ProductTree>(request, response), productTree); 
        model.addAttribute("page", page);
		return "modules/pro/productTreeList";
	}

	@RequiresPermissions("pro:productTree:view")
	@RequestMapping(value = "form")
	public String form(ProductTree productTree, Model model) {
		model.addAttribute("productTree", productTree);
		
		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);
		return "modules/pro/productTreeForm";
	}

	@RequiresPermissions("pro:productTree:edit")
	@RequestMapping(value = "save")
	public String save(ProductTree productTree, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productTree)){
			return form(productTree, model);
		}
		productTreeService.save(productTree);
		addMessage(redirectAttributes, "保存产品流管理成功");
		return "redirect:"+Global.getAdminPath()+"/pro/productTree/?repage";
	}
	
	@RequiresPermissions("pro:productTree:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		productTreeService.delete(id);
		addMessage(redirectAttributes, "删除产品流管理成功");
		return "redirect:"+Global.getAdminPath()+"/pro/productTree/?repage";
	}

	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(String module, @RequestParam(required=false) String extId, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ProductTree> list = productTreeService.findAll();
		for (int i=0; i<list.size(); i++){
			ProductTree e = list.get(i);
			if (extId == null || (extId!=null && !extId.equals(e.getId())) && e.getParentIds().indexOf(","+extId+",")==-1){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParent()!=null?e.getParent().getId():0);
				map.put("name", e.getId().equals(ProductTree.SYS_ID)?"产品树":e.getProduct().getSerialNum());
				mapList.add(map);
			}
		}
		return mapList;
	}
}
