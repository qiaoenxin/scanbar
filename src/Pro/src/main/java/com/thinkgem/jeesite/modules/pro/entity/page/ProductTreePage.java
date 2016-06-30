package com.thinkgem.jeesite.modules.pro.entity.page;

import com.thinkgem.jeesite.modules.pro.entity.Product;

public class ProductTreePage
{

    private String treeId;

    private String id;

    private String parent;

    private String name;

    private int number;

    private Product product;

    public ProductTreePage()
    {
        this(null, null, null, null, 0);
    }

    public Product getProduct()
    {
        return product;
    }

    public int getSnpNum()
    {
        return number / product.getSnpNum();
    }

    public int getModNum()
    {
        return number % product.getSnpNum();
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public ProductTreePage(String treeId, String id, String parent, String name, int number)
    {
        super();
        this.treeId = treeId;
        this.id = id;
        this.parent = parent;
        this.name = name;
        this.number = number;
    }

    public ProductTreePage(String treeId, String id, String parent, String name, int number,
                           Product product)
    {
        super();
        this.id = id;
        this.parent = parent;
        this.name = name;
        this.number = number;
        this.product = product;
    }

    public String getTreeId()
    {
        return treeId;
    }

    public void setTreeId(String treeId)
    {
        this.treeId = treeId;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getParent()
    {
        return parent;
    }

    public void setParent(String parent)
    {
        this.parent = parent;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

}
