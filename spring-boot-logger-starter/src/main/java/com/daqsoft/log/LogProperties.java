package com.daqsoft.log;

import com.daqsoft.log.util.config.FileProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Created by ShawnShoper on 2016/12/19 0019.
 */
@ConfigurationProperties(prefix = "com.daqsoft.log")
public class LogProperties extends com.daqsoft.log.util.config.LogProperties {
    //    private Target target;
//    private String host;
//    private int port;
//    private String application;
    @NestedConfigurationProperty
    private FileProperties file;
//    private String partten;

    public FileProperties getFile() {
        return file;
    }

    public void setFile(FileProperties file) {
        this.file = file;
    }
//
//    public String getPartten() {
//        return partten;
//    }
//
//    public void setPartten(String partten) {
//        this.partten = partten;
//    }
//
//
//    public Target getTarget() {
//        return target;
//    }
//
//    public void setTarget(Target target) {
//        this.target = target;
//    }
//
//    public String getHost() {
//        return host;
//    }
//
//    public void setHost(String host) {
//        this.host = host;
//    }
//
//    public int getPort() {
//        return port;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    public String getApplication() {
//        return application;
//    }
//
//    public void setApplication(String application) {
//        this.application = application;
//    }
}
