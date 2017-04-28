package com.daqsoft.log.util.appender;

/**
 * Created by ShawnShoper on 2017/4/28.
 * 文件容量
 */
public enum FileUnit {
    KB("kb", 1024), MB("kb", 1024 * KB.size), GB("gb", 1024 * MB.size), TB("tb", 1024 * GB.size);
    String name;
    int size;

    FileUnit(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}
