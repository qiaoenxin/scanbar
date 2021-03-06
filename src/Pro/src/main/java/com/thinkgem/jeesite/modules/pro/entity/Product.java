/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.thinkgem.jeesite.modules.pro.entity;


import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.persistence.IdEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;


/**
 * 产品管理Entity
 * 
 * @author Generate Tools
 * @version 2016-03-12
 */
@Entity
@Table(name = "pro_product")
public class Product extends IdEntity<Product>
{

    private static final long serialVersionUID = 1L;

    /**
     * 单品
     */
    public static final int ASSY_SIMPLE = 0;

    /**
     * 组装品
     */
    public static final int ASSY_COM = 1;

    public static final String FLOW_D = "1";

    public static final String FLOW_W = "2";
    
    public static final String FLOW_C = "4";

    /**
     * 原料
     */
    public static final int TYPE_META = 0;

    /**
     * 半成品
     */
    public static final int TYPE_MID_PRODUCT = 1;

    /**
     * 产成品
     */
    public static final int TYPE_PRODUCT = 2;

    private String name; // 产品名称

    private String unionName;// 统称，可空

    private String serialNum;// 编号

    private Integer snpNum; // snp数量

    private int type; // 种类

    private int assy; // 是否组合产品

    private String unit;// 数量单位

    private String machine; // 车型
    
    private boolean merge;

    private String bomString;

    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;

    private String field6;
    
    private transient String showBom;
    
    private transient Bom bom;
    
    @Transient
    public int getRealSnpNum(){
    	if(null != this.getBom() && null != this.getBom().getPrintSnpNum() && !Integer.valueOf(0).equals(this.getBom().getPrintSnpNum())){
    		return this.getBom().getPrintSnpNum();
    	}else{
    		return snpNum;
    	}
    }

    public Product()
    {
        super();
    }

    public Product(String id)
    {
        this();
        this.id = id;
    }

    @ExcelField(title = "名称", align = 2, sort = 50)
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @ExcelField(title = "编号", align = 2, sort = 60)
    public String getSerialNum()
    {
        return serialNum;
    }

    public void setSerialNum(String serialNum)
    {
        this.serialNum = serialNum;
    }

    @ExcelField(title = "SNP数量", align = 2, sort = 70)
    public Integer getSnpNum()
    {
        return snpNum;
    }

    public void setSnpNum(Integer snpNum)
    {
        this.snpNum = snpNum;
    }

    @ExcelField(title = "ASSY", align = 2, sort = 70)
    public int getAssy()
    {
        return assy;
    }

    public void setAssy(int assy)
    {
        this.assy = assy;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getMachine()
    {
        return machine;
    }

    public void setMachine(String machine)
    {
        this.machine = machine;
    }

    public boolean getMerge() {
		return merge;
	}

	public void setMerge(boolean merge) {
		this.merge = merge;
	}

	@ExcelField(title = "车种", align = 2, sort = 70)
    public String getField1()
    {
        return field1;
    }

    public void setField1(String field1)
    {
        this.field1 = field1;
    }

    public String getField2()
    {
        return field2;
    }

    public void setField2(String field2)
    {
        this.field2 = field2;
    }

    public String getUnionName()
    {
        return unionName;
    }

    public void setUnionName(String unionName)
    {
        this.unionName = unionName;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getField3()
    {
        return field3;
    }

    public void setField3(String field3)
    {
        this.field3 = field3;
    }

    public String getField4()
    {
        return field4;
    }

    public void setField4(String field4)
    {
        this.field4 = field4;
    }

    public String getField5()
    {
        return field5;
    }

    public void setField5(String field5)
    {
        this.field5 = field5;
    }

    public String getField6()
    {
        return field6;
    }

    public void setField6(String field6)
    {
        this.field6 = field6;
    }

    public String getBomString()
    {
        return bomString;
    }

    @Transient
    public void setBomString(String bomString)
    {
        this.bomString = bomString;
        bom = null;
    }
    
    @Transient
    public String getShowBom()
    {
        return showBom;
    }

    @Transient
    public void setShowBom(String showBom)
    {
        this.showBom = showBom;
    }

    @Transient
    public Bom getBom()
    {
        if (bom == null && bomString != null && !"".equals(bomString))
        {
            bom = new Bom(bomString);
        }
        return bom;
    }

    public void setBom(Bom bom)
    {
        this.bom = bom;
        this.bomString = bom.toJson();
    }

    public static class Bom
    {
    	public static final String PRINTCARD_ZHI_CHENG = "制程卡";
    	
    	public static final String PRINTCARD_ZU_ZHUANG = "组装卡";
    	
        // 是否打印样品卡
        private boolean isPrint;

        // 操作
        private String action;

        // 打印生产卡
        private String printCard;
        
        // 打印SNP数量
        private Integer printSnpNum;

        // 属性
        private Map<String, String> properties;

        public String toJson()
        {
            return JSONObject.toJSONString(this);
        }

        public Bom()
        {
            super();
        }

        public Bom(boolean isPrint, String action, String printCard, Integer printSnpNum,
                   Map<String, String> properties)
        {
            super();
            this.isPrint = isPrint;
            this.action = action;
            this.printCard = printCard;
            this.printSnpNum = printSnpNum;
            this.properties = properties;
        }

        public Bom(String json)
        {
            Bom bom = JSONObject.parseObject(json, Bom.class);
            this.isPrint = bom.isPrint;
            this.action = bom.action;
            this.printCard = bom.printCard;
            this.printSnpNum = bom.printSnpNum;
            this.properties = bom.properties;
        }

        public Map<String, String> getProperties()
        {
            return properties;
        }

        public void setProperties(Map<String, String> properties)
        {
            this.properties = properties;
        }

        public boolean isPrint()
        {
            return isPrint;
        }

        public void setPrint(boolean isPrint)
        {
            this.isPrint = isPrint;
        }

        public String getAction()
        {
            return action;
        }

        public void setAction(String action)
        {
            this.action = action;
        }

        public String getPrintCard()
        {
            return printCard;
        }

        public void setPrintCard(String printCard)
        {
            this.printCard = printCard;
        }

        public Integer getPrintSnpNum()
        {
            return printSnpNum;
        }

        public void setPrintSnpNum(Integer printSnpNum)
        {
            this.printSnpNum = printSnpNum;
        }
    }
}
