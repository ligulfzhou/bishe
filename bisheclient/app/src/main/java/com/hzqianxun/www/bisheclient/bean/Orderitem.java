package com.hzqianxun.www.bisheclient.bean;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by ubuntu on 15-6-10.
 */
public class Orderitem {
    @JsonProperty("nid")
    private Integer orderitemid;

    @JsonProperty("ngood_id")
    private Integer goodid;

    @JsonProperty("ncount")
    private Integer count;


    public Integer getOrderitemid() {
        return orderitemid;
    }

    public void setOrderitemid(Integer orderitemid) {
        this.orderitemid = orderitemid;
    }

    public Integer getGoodid() {
        return goodid;
    }

    public void setGoodid(Integer goodid) {
        this.goodid = goodid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
