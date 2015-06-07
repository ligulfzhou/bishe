package com.hzqianxun.www.bisheclient.bean;


import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by ubuntu on 15-6-3.
 */

public class User implements Serializable {
    @JsonProperty("nid")
    private Integer userid;

    @JsonProperty("cemail")
    private String useremail;

    @JsonProperty("cname")
    private String username;

    @JsonProperty("dcreate_at")
    private Integer create_at;

    @JsonProperty("nrole")
    private Integer userrole;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Integer create_at) {
        this.create_at = create_at;
    }

    public Integer getUserrole() {
        return userrole;
    }

    public void setUserrole(Integer userrole) {
        this.userrole = userrole;
    }
}
