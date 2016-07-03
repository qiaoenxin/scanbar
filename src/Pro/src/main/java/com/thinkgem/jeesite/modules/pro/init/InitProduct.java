package com.thinkgem.jeesite.modules.pro.init;

import java.io.File;
import java.util.List;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.pro.entity.Product;
import com.thinkgem.jeesite.modules.pro.entity.Product.Bom;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.service.ProductService;
import com.thinkgem.jeesite.modules.pro.service.ProductTreeService;

public class InitProduct {

	private static ProductService productService = SpringContextHolder.getBean(ProductService.class);
	
	private static ProductTreeService productTreeService = SpringContextHolder.getBean(ProductTreeService.class);

	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	public void init(){
		try {
			Resource resource = resourceLoader.getResource("data.xlsx");
			File file = resource.getFile();
//			File file = new File("E:/workhome/asd/src/Pro/src/main/resources/data.xlsx");
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<ProductExcel> list = ei.getDataList(ProductExcel.class);
			System.out.println(list.size());
			for(ProductExcel pro : list){
				try {
					
					Product product = checkCreateProduct(true, "100", pro.getMachine(), pro.getProductName(), pro.getProductSerialNum(), pro.getProductNum(), null, Product.TYPE_PRODUCT);
					Product preProduct = product;
					if (!StringUtils.isEmpty(pro.getAssembleName())) {
						preProduct = checkCreateProduct(true, "4", pro.getMachine(), pro.getAssembleName(), pro.getAssembleSerialNum(), pro.getAssembleNum(), preProduct, Product.TYPE_MID_PRODUCT);
					}
					
					if (!StringUtils.isEmpty(pro.getBendName())) {
						preProduct = checkCreateProduct(true, "2", pro.getMachine(), pro.getBendName(), pro.getBendSerialNum(), pro.getBendNum(), preProduct, Product.TYPE_MID_PRODUCT);
					}
					
					if (!StringUtils.isEmpty(pro.getNylonName())) {
						preProduct = checkCreateProduct(true, "5", pro.getMachine(), pro.getNylonName(), pro.getNylonSerialNum(), pro.getNylonNum(), preProduct, Product.TYPE_MID_PRODUCT);
					}
					
					if (!StringUtils.isEmpty(pro.getTerminalName())) {
						preProduct = checkCreateProduct(true, "1", pro.getMachine(), pro.getTerminalName(), pro.getTerminalSerialNum(), pro.getTerminalNum(), preProduct, Product.TYPE_MID_PRODUCT);
					}
					
					if (!StringUtils.isEmpty(pro.getMaterialName())) {
						preProduct = checkCreateProduct(false, "0", pro.getMachine(), pro.getMaterialName(), pro.getMaterialSerialNum(), pro.getMaterialNum(), preProduct, Product.TYPE_META);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Product checkCreateProduct(boolean isPrint, String action, String machine, String name, String serialNum, String number, Product parent, int type){
		List<Product> listProduct = productService.findByName(name);
		if (listProduct.size() > 0) {
			return listProduct.get(0);
		}else{
			Product product = new Product();
			product.setName(name);
			product.setSerialNum(serialNum);
			product.setSnpNum(10);
			product.setMachine(machine);
			product.setBom(new Bom(isPrint, action, null, null, null));
			product.setType(type);
			
			productService.save(product);
			
			if(parent != null){
				ProductTree tree = new ProductTree();
				tree.setProduct(product);
				tree.setNumber(Integer.valueOf(number));
				tree.setParent(parent);
				
				productTreeService.save(tree);
			}
			
			
			return product;
		}
		
	}
	
	public static void main(String[] args) {
		new InitProduct().init();
	}
	
}
