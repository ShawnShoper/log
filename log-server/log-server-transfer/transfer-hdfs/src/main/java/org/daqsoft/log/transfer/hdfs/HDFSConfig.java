package org.daqsoft.log.transfer.hdfs;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by ShawnShoper on 2017/5/25.
 */
@ConfigurationProperties(prefix = "com.cloudera.hdfs")
public class HDFSConfig {
    private static final String FS_URL_KEY = "fs.defaultFS";
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
