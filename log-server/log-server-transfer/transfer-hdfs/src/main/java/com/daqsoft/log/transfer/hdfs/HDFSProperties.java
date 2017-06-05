package com.daqsoft.log.transfer.hdfs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by ShawnShoper on 2017/5/25.
 */
@ConfigurationProperties(prefix = "com.cloudera.hdfs")
@Component
public class HDFSProperties {
    public static final String FS_URL_KEY = "fs.defaultFS";
    private String url;

    public static String getFsUrlKey() {
        return FS_URL_KEY;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
