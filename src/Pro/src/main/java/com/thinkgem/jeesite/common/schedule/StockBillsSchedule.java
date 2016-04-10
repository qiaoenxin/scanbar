package com.thinkgem.jeesite.common.schedule;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.pro.entity.Stock;
import com.thinkgem.jeesite.modules.pro.entity.StockBills;
import com.thinkgem.jeesite.modules.pro.service.StockBillsService;
import com.thinkgem.jeesite.modules.pro.service.StockService;

public class StockBillsSchedule {

	public static boolean isRunning = false;
			
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static StockBillsService stockBillsService = SpringContextHolder
			.getBean(StockBillsService.class);
	private static StockService stockService = SpringContextHolder
			.getBean(StockService.class);

	/**
	 * 定时任务执行的主入口
	 */
	public void start() {
		Date createDate = new Date();
		logger.info("---开始扎帐("+createDate+")---");
		isRunning = true;
		try {
			List<Stock> stoctList = stockService.find(new Page<Stock>(), new Stock())
					.getList();
			Date prevDate = stockBillsService.prevBillsDate();
			List<StockBills> prevBills = stockBillsService.prevBills(prevDate);
			
			List<StockBills> stockBillsList = Lists.newArrayList();
			for (Stock stock : stoctList) {
				StockBills stockBills = new StockBills();
				stockBills.setProduct(stock.getProduct());
				stockBills.setNumber(stock.getNumber());
				stockBills.setUseNumber(stock.getUseNumber());
				stockBills.setPrevNumber(prevNumber(stock, prevBills));
				stockBills.setFromDate(prevDate);
				stockBills.setCreateDate(createDate);
				stockBillsList.add(stockBills);
			}
			stockBillsService.save(stockBillsList);
			logger.info("---扎帐结束("+createDate+")---");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("扎帐异常",e);
		}finally{
			isRunning = false;
		}
	}

	private int prevNumber(Stock stock, List<StockBills> prevBills) {
		for(StockBills bill : prevBills){
			if(bill.getProduct().getId().equals(stock.getProduct().getId())){
				return bill.getNumber();
			}
		}
		return 0;
	}

}
