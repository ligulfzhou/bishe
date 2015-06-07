package com.hzqianxun.www.bisheclient.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by ubuntu on 2015/5/25.
 */
public class Order implements Serializable {
    @JsonProperty("nid")
    private Integer orderid;

    @JsonProperty("ncreate_at")
    private Integer create_at;

    @JsonProperty("nuser_id")
    private Integer userid;

    @JsonProperty("dtotal")
    private Double total;

    @JsonProperty("nhandlered")
    private Integer handlered;


    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Integer create_at) {
        this.create_at = create_at;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getHandlered() {
        return handlered;
    }

    public void setHandlered(Integer handlered) {
        this.handlered = handlered;
    }
}
