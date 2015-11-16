package com.hzqianxun.www.bisheclient.bean;

/**
 * Created by ubuntu on 15-6-5.
 */
public class GoodInCart {
    Integer ngoodid;
    Integer ncount;

    public GoodInCart() {
    }

    public GoodInCart(Integer goodid, Integer count) {
        super();
        this.ngoodid = goodid;
        this.ncount = count;
    }

    public Integer getNgoodid() {
        return ngoodid;
    }

    public void setNgoodid(Integer ngoodid) {
        this.ngoodid = ngoodid;
    }

    public Integer getNcount() {
        return ncount;
    }

    public void setNcount(Integer ncount) {
        this.ncount = ncount;
    }
}
