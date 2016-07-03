/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.web;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.pro.entity.ProductTree;
import com.thinkgem.jeesite.modules.pro.entity.Production;
import com.thinkgem.jeesite.modules.pro.entity.ProductionDetail;
import com.thinkgem.jeesite.modules.pro.entity.page.ProductionPlanTreePage;
import com.thinkgem.jeesite.modules.pro.service.ProductService;
import com.thinkgem.jeesite.modules.pro.service.ProductTreeService;
import com.thinkgem.jeesite.modules.pro.service.ProductionDetailService;
import com.thinkgem.jeesite.modules.pro.service.ProductionService;

/**
 * 生产计划详情Controller
 * @author Generate Tools
 * @version 2016-03-12
 */
@Controller
@RequestMapping(value = "${adminPath}/pro/productionPlanTree")
public class ProductionPlanTreeController extends BaseController {

	@Autowired
	private ProductTreeService productTreeService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductionService productionService;
	
	@Autowired
	private ProductionDetailService productionDetailService;
	
	
	
	@RequiresPermissions("pro:productTree:view")
	@RequestMapping(value = {"list", ""})
	public String list(String productionId, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Production production = productionService.get(productionId);
		
        model.addAttribute("list", makePlanList(production, production.getPlan().getEndDate()));
        
		return "modules/pro/productionPlanTreeList";
	}
	
	
	@RequiresPermissions("pro:productTree:view")
	@RequestMapping(value = "previewList")
	public String previewList(String productId, int number, Date date, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Production production = new Production();
		production.setProduct(productService.get(productId));
		production.setNumber(number);
		
        model.addAttribute("list", makePlanList(production, date));
        
		return "modules/pro/productionPlanTreeList";
	}
	
	@RequiresPermissions("pro:productTree:view")
	@RequestMapping(value = "createPlanDetail")
	public String createPlanDetail(String productionId, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Production production = productionService.get(productionId);
		
		if(Production.PRODUCTION_NO != production.getIsProducing()){
			throw new RuntimeException();
		}
		//计数产品，用于编号
		int count = 0;
		List<ProductionPlanTreePage> list = makePlanList(production, production.getPlan().getEndDate());
		List<ProductionDetail> detailList = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			
			ProductionPlanTreePage treePage = list.get(i);
			double toatl = treePage.getNumber();
			double snp = treePage.getProduct().getRealSnpNum();
			
			int length = (int) Math.ceil(toatl/snp);
			for (int j = 0; j < length; j++) {
				ProductionDetail detail = new ProductionDetail();
				detail.setProduction(production);
				detail.setSerialNum(toSeq(production.getSerialNum(), count, 4));
				detail.setCompleteNum(0);
				detail.setUnqualifiedNum(0);
				if(!"".equals(treePage.getTreeId())){
					detail.setProductTree(new ProductTree(treePage.getTreeId()));
				}
				if (j == (length-1)) {
					detail.setNumber((int) (toatl-snp*j));
				}else{
					detail.setNumber((int) snp);
				}
				
				count++;
				
				detail.setDate(treePage.getDate());
				
				detailList.add(detail);
			}
			
		}
		productionDetailService.save(detailList);
		production.setIsProducing(Production.PRODUCTION_YES);
		productionService.save(production);
        
		return "redirect:"+Global.getAdminPath()+"/pro/productionPlan/list";
	}
	
	
	private List<ProductionPlanTreePage> makePlanList(Production production, Date date){
		//批次数量
		int picinumber = production.getNumber();
		
		List<ProductionDetail> details = productionDetailService.findByProductionId(production.getId());
		
		List<ProductionPlanTreePage> list = Lists.newArrayList();
		
		if (null == date) {
			date = new Date();
		}
		String topId = IdGen.uuid();
		list.add(new ProductionPlanTreePage("", topId, "", production.getProduct(), picinumber, date, countComplateNum(details, "")));
		List<ProductTree> roots = productTreeService.findChildrensByProductId(production.getProduct().getId());
		for(ProductTree root : roots){
			int totalNum = root.getNumber()*picinumber;
			String id = IdGen.uuid();
			list.add(new ProductionPlanTreePage(root.getId(),id,topId,root.getProduct(),totalNum,toPreDate(date),countComplateNum(details, root.getId())));
			List<ProductTree> childrens = productTreeService.findChildrensByProductId(root.getProduct().getId());
			for(ProductTree c : childrens){
				recursiveChildren(id,c,list, totalNum, toPreDate(date), details);
			}
		}
		
		return list;
		
	}

	
	public void recursiveChildren(String parentId,ProductTree productTree,List<ProductionPlanTreePage> list, int number, Date date, List<ProductionDetail> details){
		String id = IdGen.uuid();
		list.add(new ProductionPlanTreePage(productTree.getId(),id,parentId,productTree.getProduct(),(productTree.getNumber() * number),date,countComplateNum(details, productTree.getId())));
		
		List<ProductTree> childrens = productTreeService.findChildrensByProductId(productTree.getProduct().getId());
		for(ProductTree c : childrens){
			recursiveChildren(id,c,list, productTree.getNumber() * number, toPreDate(date), details);
		}
	}
	
	/**
	 * 统计完成数量
	 * @param list
	 * @param treeId
	 * @return
	 */
	private int countComplateNum(List<ProductionDetail> list, String treeId){
		int count = 0;
		for (ProductionDetail productionDetail : list) {
			if(null == productionDetail.getProductTree() && "".equals(treeId)){
				count += productionDetail.getCompleteNum();
			}
			
			if(null != productionDetail.getProductTree() && productionDetail.getProductTree().getId().equals(treeId)){
				count += productionDetail.getCompleteNum();
			}
			
		}
		
		return count;
	}
	
	/**
	 * 获取日期前一天
	 * @param date
	 * @return
	 */
	private Date toPreDate(Date date){
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(Calendar.DATE, -1);
		 return calendar.getTime();
	}
	
	/**
	 * 整数补位
	 * @param num
	 * @param count
	 * @return
	 */
	private String toSeq(String prefix, int num, int count){
		double power = Math.pow(10, count);
		if(num >= power){
			throw new RuntimeException();
		}
		String str = String.valueOf(num);
		int patch = count-str.length();
		StringBuilder builder = new StringBuilder(10);
		builder.append(prefix);
		for(int i =0; i < patch; i++){
			builder.append("0");
		}
		builder.append(str);
		return builder.toString();
	}

	
}
