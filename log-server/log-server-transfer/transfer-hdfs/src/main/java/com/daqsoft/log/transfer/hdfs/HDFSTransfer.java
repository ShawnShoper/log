package com.daqsoft.log.transfer.hdfs;

import com.daqsoft.commons.core.DateUtil;
import com.daqsoft.log.core.serialize.Log;
import com.daqsoft.log.transfer.Transfer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;


/**
 * Created by ShawnShoper on 2017/5/25.
 */
public class HDFSTransfer implements Transfer {
    public HDFSTransfer(HDFSProperties hdfsProperties) {
        this.hdfsProperties = hdfsProperties;
    }

    public HDFSTransfer() {
    }

    HDFSProperties hdfsProperties;
    FileSystem fileSystem;
    final Path dir = new Path("/user/cloudera/kafka-log");
    String time = null;
    public void initilize() {
        Configuration configuration = new Configuration();
        configuration.set(HDFSProperties.FS_URL_KEY, hdfsProperties.getUrl());
        try {
            fileSystem = FileSystem.get(configuration);
            if (!fileSystem.exists(dir) && fileSystem.mkdirs(dir))
                throw new RuntimeException("Dir " + dir.getName() + " mkdir failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Log log) throws IOException {
        getFile(log.getApplication());
    }

    private void getFile(String application) {
        if(Objects.isNull(time))
            time = DateUtil.timeToString("yyyy-MM-dd",System.currentTimeMillis());
    }

    @Override
    public void destory() {

    }
}
