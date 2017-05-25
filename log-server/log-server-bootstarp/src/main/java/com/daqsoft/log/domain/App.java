package com.daqsoft.log.domain;

import java.io.Serializable;

/**
 * Created by ShawnShoper on 2017/5/24.
 */
public class App implements Serializable {
    private String appName;
    private String token;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
