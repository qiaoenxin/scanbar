/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.thinkgem.jeesite.common.persistence.Page;
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
import com.thinkgem.jeesite.modules.pro.entity.Product.Bom;
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
	public String list(Product product, HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		List<ProductTreePage> list = Lists.newArrayList();
		Page<ProductTreePage> page = new Page<ProductTreePage>();
		
		// 1. 查询所有的产品
		Page<Product> page_products = productService.find(new Page<Product>(request, response), product);
		List<Product> products = page_products.getList();
		for(Product productInfo : products)
		{
	        // 2. 根据当前产品，循环查询下面的子节点
			String id = IdGen.uuid();
			
			// 设置bomString
			productInfo.setShowBom(getBomString(productInfo));
			
			// 设置父节点
			list.add(new ProductTreePage(productInfo.getId(),id,"",productInfo.getName(),1,productInfo));
			
			// 查询二级节点
			List<ProductTree> childrens = productTreeService.findChildrensByProductId(productInfo.getId());
			
			// 查询下面的子节点
			for(ProductTree c : childrens){
				recursiveChildren(id,c,list, c.getNumber());
			}
		}
		page.setList(list);
		page.setCount(page_products.getCount());
		page.setPageNo(page_products.getPageNo());
		page.setPageSize(page_products.getPageSize());
		
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        
		return "modules/pro/productTreeList";
	}
	
	/**
	 * 拼接属性
	 * 
	 * @param productInfo 产品
	 * @return string
	 * @see
	 */
	private String getBomString(Product productInfo)
	{
	    StringBuffer bomString = new StringBuffer();
	    Bom bom = productInfo.getBom();
	    if (null == bom)
        {
	        return bomString.toString();
        }
	    
	    // 操作为端末
        if ("1".equals(productInfo.getBom().getAction()) && null != productInfo.getBom().getProperties())
        {
            bomString.append("HPC:");
            bomString.append(productInfo.getBom().getProperties().get("HPC"));
            bomString.append(",");
            bomString.append("ISO/ISO:");
            bomString.append(productInfo.getBom().getProperties().get("ISO"));
            bomString.append(",");
            bomString.append("PCO:");
            bomString.append(productInfo.getBom().getProperties().get("PCO"));
            bomString.append(",");
            bomString.append("标识:");
            bomString.append(productInfo.getBom().getProperties().get("biaoShi"));
            bomString.append(",");
            bomString.append("端末:");
            bomString.append(productInfo.getBom().getProperties().get("duanMo"));
            bomString.append(",");
            bomString.append("烘护套:");
            bomString.append(productInfo.getBom().getProperties().get("hongHuTao"));
            bomString.append(",");
            bomString.append("印字:");
            bomString.append(productInfo.getBom().getProperties().get("yinZi"));
        }
        
        // 操作为弯曲
        if ("2".equals(productInfo.getBom().getAction()) && null != productInfo.getBom().getProperties())
        {
            bomString.append("规格");
            bomString.append(productInfo.getBom().getProperties().get("guiGe"));
        }
        
        return bomString.toString();
	}
	
	public void recursiveChildren(String parentId,ProductTree productTree,List<ProductTreePage> list, int number){
		String id = IdGen.uuid();
		// 设置bomString
		productTree.getProduct().setShowBom(getBomString(productTree.getProduct()));
		list.add(new ProductTreePage(productTree.getId(),id,parentId,productTree.getProduct().getName(),productTree.getNumber() * number,productTree.getProduct()));
		
		List<ProductTree> childrens = productTreeService.findChildrensByProductId(productTree.getProduct().getId());
		for(ProductTree c : childrens){
			recursiveChildren(id,c,list, productTree.getNumber() * number);
		}
	}

	@RequiresPermissions("pro:productTree:view")
	@RequestMapping(value = "form")
	public String form(ProductTree productTree, Model model) {
	    productTree = new ProductTree();
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
				pTree.setProduct(parent);
				pTree.setNumber(1);
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
				map.put("name", p.getName());
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
			map.put("name", p.getName());
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
