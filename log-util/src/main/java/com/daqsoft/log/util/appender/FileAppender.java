package com.daqsoft.log.util.appender;

import com.daqsoft.log.util.Log;
import com.daqsoft.log.util.config.FileProperties;
import com.daqsoft.log.util.config.LogProperties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ShawnShoper on 2017/4/20.
 */
public class FileAppender extends Appender {
    FileProperties fileProperties;
    public FileAppender(LogProperties logProperties) {
        super(logProperties);
    }

    @Override
    public void init() {
        this.fileProperties = logProperties.getFileProperties();
        String fileDir = this.fileProperties.getFileDir();
        File file = new File(fileDir);
        if(!file.exists())file.mkdirs();

    }

    @Override
    public void write(Log log) throws IOException {
        File file = new File("D:/a.log");
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        fileOutputStream.write(log.toString().getBytes());
    }
}
