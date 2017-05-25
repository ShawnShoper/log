package com.daqsoft.log.service;

import com.daqsoft.log.dao.AppMapper;
import com.daqsoft.log.domain.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ShawnShoper on 2017/5/24.
 */
@Service
public class AppService {
    @Autowired
    AppMapper appMapper;

    public App findAppByAppName(String appName) {
        return appMapper.findAppByAppName(appName);
    }

    public boolean validateByAppIDAndCert(String appID, String cert) {
        if (appMapper.validateByAppIDAndCert(appID, cert) == 1) return true;
        return false;
    }
    @Transactional(rollbackFor = Exception.class)
    public void ins() throws Exception {
        appMapper.insert("123","123","123");
        throw  new Exception("1234");
    }
}
