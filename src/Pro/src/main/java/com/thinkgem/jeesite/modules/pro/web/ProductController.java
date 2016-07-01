/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.entity.ProductTreeModel;
import com.thinkgem.jeesite.modules.pro.service.ProductService;
import com.thinkgem.jeesite.modules.pro.service.ProductTreeService;

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
	
	@Autowired
    private ProductTreeService productTreeService;
	
	@Autowired
    private DictService dictService;
	
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
		Dict dict = new Dict();
        dict.setType("flow_type");
	    List<Dict> dicts =  dictService.find(dict);
		model.addAttribute("dicts", dicts);
		return "modules/pro/productForm";
	}
	

    @RequiresPermissions("pro:product:view")
    @RequestMapping(value = "productTreeForm")
    public String bomForm(Product product, Model model) 
    {
        // 获取所有子节点
        List<ProductTree> ProductTrees = productTreeService.findChildrensByProductId(product.getId());
        model.addAttribute("productTrees", ProductTrees);
        
        // 获取父节点
        model.addAttribute("product", product);
        
        // 获取子节点
        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);
        return "modules/pro/productTreeForm";
    }
	
	@RequiresPermissions("pro:product:edit")
	@RequestMapping(value = "flow")
	public String flow(Product product, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("product", product);
		
		JSONArray flows = new JSONArray();
		
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
	public String save(Product product,MapVo mapVo, Model model, RedirectAttributes redirectAttributes) {
	    
		if (!beanValidator(model,  product)){
			return form(product, model);
		}
		
		product.getBom().setProperties(mapVo.getProperties());
		
		// bom属性
		product.setBomString(product.getBom().toJson());
		
		productService.save(product);
		addMessage(redirectAttributes, "保存产品管理成功");
		return "redirect:"+Global.getAdminPath()+"/pro/product/?repage";
	}
	
    @RequiresPermissions("pro:product:edit")
    @RequestMapping(value = "productTreeSave")
    public String productTreeSave(Product product, ProductTreeModel productTreeModel, Model model, RedirectAttributes redirectAttributes) 
    {
        if (!beanValidator(model,  product)){
            return form(product, model);
        }
        
        // 获取当前页面传递过来的子节点
        List<ProductTree> productTreeList = productTreeModel.getProductTreeList();
        Map<ProductTree, ProductTree> map = new HashMap<ProductTree, ProductTree>();
        for (ProductTree productTree : productTreeList)
        {
            // 父节点和子节点不是同一个产品
            if ((product.getId()).equals(productTree.getProduct().getId()))
            {
                addMessage(redirectAttributes, "保存BOM失败，父节点和子节点不能相同!");
                return "redirect:"+Global.getAdminPath()+"/pro/product/?repage";
            }
            
            productTree.setParent(product);
            map.put(productTree, productTree);
        }  
        
        if (map.size() != productTreeList.size())
        {
            addMessage(redirectAttributes, "保存BOM失败，子节点不能相同!");
            return "redirect:"+Global.getAdminPath()+"/pro/product/?repage";
        }
        
        // 批量保存
        productTreeService.saveList(productTreeList);
        
        // 删除多余的子节点
        productTreeService.deleteByDelFlag(ProductTree.SYS_ID);
        
        addMessage(redirectAttributes, "保存BOM成功!");
        return "redirect:"+Global.getAdminPath()+"/pro/product/?repage";
    }
	
	@RequiresPermissions("pro:product:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		productService.delete(id);
		addMessage(redirectAttributes, "删除产品管理成功");
		return "redirect:"+Global.getAdminPath()+"/pro/product/?repage";
	}
	
	@RequiresPermissions("pro:product:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Product product, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "产品数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx"; 
    		List<Product> productList = productService.findAll(); 
    		new ExportExcel("产品数据", Product.class).setDataList(productList).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出产品失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/pro/product/?repage";
    }

	@RequiresPermissions("pro:product:view")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			Date createDate = new Date();
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Product> list = ei.getDataList(Product.class);
			for (Product product : list){
				try{
					product.setCreateDate(createDate);
					product.setCreateBy(UserUtils.getUser());
					productService.save(product);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>产品"+product.getName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>产品"+product.getName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条产品，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条产品"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/pro/product/?repage";
    }
	
	@RequiresPermissions("pro:product:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "产品数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList();
    		new ExportExcel("产品数据", Product.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/pro/product/?repage";
    }

}
