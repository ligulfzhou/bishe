package com.hzqianxun.www.bisheclient.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by ubuntu on 2015/5/25.
 */
public class Good implements Serializable {

    @JsonProperty("nid")
    private Integer goodid;

    @JsonProperty("cname")
    private String goodname;

    @JsonProperty("dprice")
    private double goodprice;

    @JsonProperty("cdesc")
    private String gooddesc;

    @JsonProperty("ncategoryid")
    private Integer categoryid;

    @JsonProperty("ncount")
    private Integer count;

    public Good() {
        super();
    }

    public Good(Integer goodid, String goodname, double goodprice, String gooddesc, Integer categoryid, Integer count) {
        super();
        this.goodid = goodid;
        this.goodname = goodname;
        this.goodprice = goodprice;
        this.gooddesc = gooddesc;
        this.categoryid = categoryid;
        this.count = count;
    }


    public Integer getGoodid() {
        return goodid;
    }

    public void setGoodid(int goodid) {
        this.goodid = goodid;
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public double getGoodprice() {
        return goodprice;
    }

    public void setGoodprice(double goodprice) {
        this.goodprice = goodprice;
    }

    public String getGooddesc() {
        return gooddesc;
    }

    public void setGooddesc(String gooddesc) {
        this.gooddesc = gooddesc;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
