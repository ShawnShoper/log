package com.daqsoft.log.util;

import com.daqsoft.log.util.constans.Constans;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by ShawnShoper on 2017/4/17.
 * 日志基本数据结构
 */
public class Log {
    //日志来源
    private String application;
    //时间戳
    private long time;
    //日志内容格式
    private String contentType = Constans.TYPE_STRING;
    //来源地址 HOST:PORT
    private String source;

    private Business business;

    public Business getBusiness() {
        return business;
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
