package com.hzqianxun.www.bisheclient.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by ubuntu on 15-6-3.
 */

public class Category implements Serializable {

    public Category(){
        super();
    }

    public Category(Integer categoryid, String categoryname){
        super();
        this.categoryid = categoryid;
        this.categoryname = categoryname;
    }

    @JsonProperty("nid")
    private Integer categoryid;

    @JsonProperty("cname")
    private String categoryname;

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
