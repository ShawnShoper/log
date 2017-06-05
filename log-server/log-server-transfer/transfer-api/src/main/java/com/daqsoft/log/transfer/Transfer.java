package com.daqsoft.log.transfer;

import com.daqsoft.log.core.serialize.Log;

import java.io.IOException;

/**
 * Created by ShawnShoper on 2017/5/25.
 *
 */
public interface Transfer {
    void initilize();

    void write(Log log) throws IOException;

    void destory();
}
