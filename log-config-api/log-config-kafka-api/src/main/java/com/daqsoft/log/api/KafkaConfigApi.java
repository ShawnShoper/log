package com.daqsoft.log.api;

import com.daqsoft.commons.responseEntity.DataResponse;

import java.util.Map;

/**
 * Created by ShawnShoper on 2017/5/18.
 */
public interface KafkaConfigApi {
    DataResponse<Map<String, Object>> getKafkaConfig(String appID);
}
