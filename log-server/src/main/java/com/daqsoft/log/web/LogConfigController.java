package com.daqsoft.log.web;

import com.daqsoft.commons.responseEntity.DataResponse;
import com.daqsoft.log.api.KafkaConfigApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ShawnShoper on 2017/5/18.
 */
@RestController
public class LogConfigController implements KafkaConfigApi {
    @RequestMapping("/kafka/config")
    public DataResponse<Map<String, Object>> getKafkaConfig(String appID) {
        DataResponse<Map<String,Object>> dataResponse = new DataResponse<>();
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        dataResponse.setData(props);
        return dataResponse;
    }
}
