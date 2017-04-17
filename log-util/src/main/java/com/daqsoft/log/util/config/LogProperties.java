package com.daqsoft.log.util.config;

import com.daqsoft.log.util.constans.Target;

/**
 * Created by ShawnShoper on 2016/12/19 0019.
 */
public class LogProperties {
    private Target target;
    private String host;
    private int port;
    private String application;
    private String logDir;

    public String getLogDir() {
        return logDir;
    }

    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
