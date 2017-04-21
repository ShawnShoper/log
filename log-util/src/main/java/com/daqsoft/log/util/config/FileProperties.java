package com.daqsoft.log.util.config;

/**
 * Created by ShawnShoper on 2017/4/21.
 */
public class FileProperties {
    enum Rolling {
        Hour, Day, Month, Year;
    }

    private String fileDir;
    private String fileName;
    private Rolling rolling;
    private String fileSize;

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Rolling getRolling() {
        return rolling;
    }

    public void setRolling(Rolling rolling) {
        this.rolling = rolling;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
