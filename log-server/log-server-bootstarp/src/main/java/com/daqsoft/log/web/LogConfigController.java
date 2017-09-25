package com.daqsoft.log.web;

import com.daqsoft.commons.core.StringUtil;
import com.daqsoft.commons.responseEntity.BaseResponse;
import com.daqsoft.commons.responseEntity.DataResponse;
import com.daqsoft.commons.responseEntity.ResponseBuilder;
import com.daqsoft.log.api.KafkaConfigApi;
import com.daqsoft.log.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ShawnShoper on 2017/5/18.
 */
@RestController
@RequestMapping
public class LogConfigController implements KafkaConfigApi {
    @Autowired
    AppService appService;

    @PostMapping(value = "/kafka/config", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DataResponse<Map<String, Object>> getKafkaConfig(@RequestParam("key") String key, @RequestParam("cert") String cert) {
        DataResponse<Map<String, Object>> custom = new DataResponse<>();
        if (StringUtil.isAnyEmpty(key, cert)) {
            custom.setCode(1);
            custom.setMessage("AppId or key is empty.");
        } else if (appService.validateByAppIDAndCert(key, cert)) {
            Map<String, Object> props = new HashMap<>();
            props.put("bootstrap.servers", "192.168.2.115:9092");
            props.put("acks", "all");
            props.put("retries", 0);
            props.put("batch.size", 16384);
            props.put("linger.ms", 1);
            props.put("buffer.memory", 33554432);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("topic", "test");
            custom.setData(props);
        } else {
            custom.setCode(1);
            custom.setMessage("AppId or key is empty.");
        }
        return custom;
    }
}
