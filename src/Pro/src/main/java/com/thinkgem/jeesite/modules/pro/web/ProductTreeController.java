/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.web;

import java.util.Iterator;
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
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.entity.page.ProductTreePage;
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
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		List<ProductTreePage> list = Lists.newArrayList();
		
		List<ProductTree> roots = productTreeService.findRoots();
		for(ProductTree root : roots){
			String id = IdGen.uuid();
			list.add(new ProductTreePage(root.getId(),id,"",root.getProduct().getSerialNum(),root.getNumber()));
			List<ProductTree> childrens = productTreeService.findChildrensByProductId(root.getProduct().getId());
			for(ProductTree c : childrens){
				recursiveChildren(id,c,list);
			}
		}
        model.addAttribute("list", list);
        
		return "modules/pro/productTreeList";
	}
	
	public void recursiveChildren(String parentId,ProductTree productTree,List<ProductTreePage> list){
		String id = IdGen.uuid();
		list.add(new ProductTreePage(productTree.getId(),id,parentId,productTree.getProduct().getSerialNum(),productTree.getNumber()));
		
		List<ProductTree> childrens = productTreeService.findChildrensByProductId(productTree.getProduct().getId());
		for(ProductTree c : childrens){
			recursiveChildren(id,c,list);
		}
	}
	

	@RequiresPermissions("pro:productTree:view")
	@RequestMapping(value = "form")
	public String form(ProductTree productTree, Model model) {
		if(productTree.getNumber() == 0){
			productTree.setNumber(1);
		}
		model.addAttribute("productTree", productTree);
		
		List<Product> productList = productService.findAll();
		productList.add(0, new Product());
		model.addAttribute("productList", productList);
		return "modules/pro/productTreeForm";
	}

	@RequiresPermissions("pro:productTree:edit")
	@RequestMapping(value = "save")
	public String save(ProductTree productTree, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productTree)){
			return form(productTree, model);
		}
		if(productTree.getParent() == null || StringUtils.isBlank(productTree.getParent().getId())){
			productTree.setNumber(1);
		}else{
			Product parent = productTree.getParent();
			List<ProductTree> list = productTreeService.findParentsByProductId(parent.getId());
			if(list.isEmpty()){
				ProductTree pTree  = new ProductTree();
				productTree.setProduct(parent);
				productTree.setNumber(1);
				productTreeService.save(pTree);
			}
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
	@RequestMapping(value = "getProductList")
	public List<Map<String, Object>> getProductList(String module, @RequestParam(required=false) String id, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		
		List<Product> productList = productService.findAll();
		
		List<Product> list = Lists.newArrayList();
		if(StringUtils.isEmpty(id)){
			List<String> ids = productTreeService.hasChildren();
			for(Iterator<Product> iter = productList.iterator();iter.hasNext();){
				Product pro = iter.next();
				if(ids.contains(pro.getId())){
					iter.remove();
				}
			}
			for(Product p : productList){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", p.getId());
				map.put("serialNum", p.getSerialNum());
				mapList.add(map);
			}
			return mapList;
		}
		//获取当前产品下的所有子节点
		List<ProductTree> childList = productTreeService.findChildrensByProductId(id);
		for(ProductTree productTree : childList){
			list.add(productTree.getProduct());
		}
		//获取当前产品的所有父节点
		List<ProductTree> parentList = productTreeService.findParentsByProductId(id);
		for(ProductTree productTree : parentList){
			if(productTree.getParent()==null){
				continue;
			}
			recursiveParent(productTree.getParent(),list);
		}
		//可选择的不能包含自身
		list.add(new Product(id));
		
		List<Product> _productList = Lists.newArrayList();
		_productList.addAll(productList);
		
		
		for(Product product : list){
			for(Product p : productList){
				if(product.getId().equals(p.getId())){
					_productList.remove(p);
					break;
				}
			}
		}
		
		for(Product p : _productList){
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", p.getId());
			map.put("serialNum", p.getSerialNum());
			mapList.add(map);
		}
		return mapList;
	}
	
	
	public void recursiveParent(Product product,List<Product> list){
		list.add(product);
		List<ProductTree> parentList = productTreeService.findParentsByProductId(product.getId());
		for(ProductTree productTree : parentList){
			if(productTree.getParent()==null){
				continue;
			}
			recursiveParent(productTree.getParent(),list);
		}
	}
	
	
	public static class Tree {

		private int id;
		
		private ProductTree productTree;
		
		private Tree parent;
		
		List<Tree> children;
		
		public Tree(ProductTree productTree){
			this.productTree = productTree;
			children = findChildren(this);
		}
		private List<Tree> findChildren(Tree tree) {
			// TODO Auto-generated method stub
			return null;
		}
		
		void showList(List<Tree> list){
			list.add(this);
			
			for(Tree tree: children){
				tree.showList(list);
			}
		}
		
	}
	
}
