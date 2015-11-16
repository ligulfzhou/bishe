package com.hzqianxun.www.bisheclient.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by ubuntu on 15-6-3.
 */
public class Session extends User implements Serializable {
    @JsonProperty("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
