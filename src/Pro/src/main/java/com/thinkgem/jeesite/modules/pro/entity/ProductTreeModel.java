/*
 * 文件名：ProductTreeModel.java 
 * 版权：Copyright by xxx 
 * 描述： 
 * 修改人：dingshulin 
 * 修改时间：2016年6月29日
 * 跟踪单号： 修改单号： 修改内容：
 */
package com.thinkgem.jeesite.modules.pro.entity;

import java.util.List;

public class ProductTreeModel
{
    private List<ProductTree> productTreeList;

    public ProductTreeModel()
    {
        super();
    }

    public ProductTreeModel(List<ProductTree> productTreeList)
    {
        super();
        this.productTreeList = productTreeList;
    }

    public List<ProductTree> getProductTreeList()
    {
        return productTreeList;
    }

    public void setProductTreeList(List<ProductTree> productTreeList)
    {
        this.productTreeList = productTreeList;
    }

}
