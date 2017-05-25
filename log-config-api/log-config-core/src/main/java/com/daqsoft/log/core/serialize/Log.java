package com.daqsoft.log.core.serialize;

import com.daqsoft.log.core.config.Constans;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by ShawnShoper on 2017/4/17.
 * 日志基本数据结构
 */
public class Log implements Serializable {
    //日志来源
    private String application;
    //时间戳
    private long time;
    //日志内容格式
    private String contentType = Constans.TYPE_STRING;
    //来源地址 HOST:PORT
    private String source;
    private int pid;
    private String className;
    private String methodName;
    private int lineNumber;
    private Business business;

    public Business getBusiness() {
        return business;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String serializer() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public static Log deserializer(String data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(data, Log.class);
    }

    @Override
    public String toString() {
        return "Log{" +
                "application='" + application + '\'' +
                ", time=" + time +
                ", contentType='" + contentType + '\'' +
                ", source='" + source + '\'' +
                ", business=" + business +
                '}';
    }
}
