package com.daqsoft.log.transfer.elasticsearch;

import com.daqsoft.log.core.config.Constans;
import com.daqsoft.log.core.serialize.Business;
import com.daqsoft.log.core.serialize.Log;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ShawnShoper on 2017/5/27.
 */
@Document(indexName = "kafka-log", type = "exernal", shards = 1, replicas = 0)
public class LogIndex {
    @Id
    private String id;
    //日志来源
    private String application;
    //时间戳
    @Field(type = FieldType.Date)
    private Date time;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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

    public LogIndex(Log log) {
        super();
        this.setId(UUID.randomUUID().toString());
        this.setApplication(log.getApplication());
        this.setBusiness(log.getBusiness());
        this.setClassName(log.getClassName());
        this.setContentType(log.getContentType());
        this.setLineNumber(log.getLineNumber());
        this.setMethodName(log.getMethodName());
        this.setPid(log.getPid());
        this.setSource(log.getSource());
        this.setTime(new Date(log.getTime()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
