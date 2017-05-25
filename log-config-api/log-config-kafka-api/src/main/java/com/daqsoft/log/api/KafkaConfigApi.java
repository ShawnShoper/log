package com.daqsoft.log.api;

import com.daqsoft.commons.responseEntity.DataResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by ShawnShoper on 2017/5/18.
 */
@FunctionalInterface
@RestController
public interface KafkaConfigApi {
    @RequestMapping(value = "/kafka/config",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    DataResponse<Map<String,Object>> getKafkaConfig(@RequestParam("key") String key, @RequestParam("cert") String cert);
}
