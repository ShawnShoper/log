package com.daqsoft.log.util.appender;

import com.daqsoft.commons.core.DateUtil;
import com.daqsoft.log.util.Log;
import com.daqsoft.log.util.config.FileProperties;
import com.daqsoft.log.util.config.LogProperties;

import java.io.*;
import java.util.Objects;

/**
 * Created by ShawnShoper on 2017/4/20.
 */
public class FileAppender extends Appender {
    FileProperties fileProperties;
    InputStream inputStream;

    public FileAppender(LogProperties logProperties) {
        super(logProperties);
    }

    @Override
    public void init() {
        this.fileProperties = logProperties.getFileProperties();
        String fileDir = this.fileProperties.getFileDir();
        File file = new File(fileDir);
        if (!file.exists()) file.mkdirs();
    }

    /**
     * 获取输出流
     *
     * @return
     */
    private InputStream getInputStream() throws FileNotFoundException {
        String fileName = fileProperties.getFileDir() + File.separator + fileProperties.getFileName();
        if (Objects.isNull(inputStream)) {
            fileName = fileProperties.getFileName();
        }
        String rollingName;
        if (Objects.nonNull(fileProperties.getRolling())) {
            rollingName = DateUtil.timeToString(fileProperties.getRolling().getPattern(), System.currentTimeMillis());


//            switch (fileProperties.getRolling()) {
//                case Hour:
//
//                    break;
//                case Day:
//                    rollingName = DateUtil.timeToString(fileProperties.getRolling().getPattern(),System.currentTimeMillis());
//                    break;
//                case Month:
//                    break;
//                case Year:
//                    break;
//                default:
//                    break;
//            }
        }
        inputStream = new FileInputStream(new File(fileName));
        return inputStream;
    }

    @Override
    public void write(Log log) throws IOException {

        File file = new File("D:/a.log");
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        fileOutputStream.write(log.toString().getBytes());
    }
}
