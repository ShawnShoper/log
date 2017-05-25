package com.daqsoft.log.util.config;


import com.daqsoft.log.core.config.Target;

/**
 * Created by ShawnShoper on 2016/12/19 0019.
 * 日志配置类
 */

public class LogProperties {
    private Target[] targets;
    private String host;
    private int port;
    private String application;
    private KafkaProperties kafka;

    private FileProperties fileProperties;
    private String partten;

    public KafkaProperties getKafka() {
        return kafka;
    }

    public void setKafka(KafkaProperties kafka) {
        this.kafka = kafka;
    }

    public FileProperties getFileProperties() {
        return fileProperties;
    }

    public String getPartten() {
        return partten;
    }

    public void setPartten(String partten) {
        this.partten = partten;
    }

    public void setFileProperties(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    public Target[] getTargets() {
        return targets;
    }

    public void setTargets(Target[] targets) {
        this.targets = targets;
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
