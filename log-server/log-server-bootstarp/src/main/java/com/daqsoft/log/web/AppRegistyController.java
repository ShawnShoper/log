package com.daqsoft.log.web;

import com.daqsoft.commons.responseEntity.DataResponse;
import com.daqsoft.log.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ShawnShoper on 2017/5/24.
 */
@RestController
public class AppRegistyController {
    @Autowired
    AppService userService;

    @RequestMapping("/registyApp")
    public DataResponse registyApp(@RequestParam("appName") String appName) {
        DataResponse dataResponse = new DataResponse();
        try {
            dataResponse.setData(userService.findAppByAppName(appName));
        } catch (Exception e) {
            dataResponse.setCode(1);
            dataResponse.setMessage(e.getMessage());
        }
        return dataResponse;
    }

    @RequestMapping("/rapp")
    public DataResponse rapp() {
        DataResponse dataResponse = new DataResponse();
        try {
            userService.ins();
        } catch (Exception e) {
            dataResponse.setCode(1);
            dataResponse.setMessage(e.getMessage());
        }
        return dataResponse;
    }
}
