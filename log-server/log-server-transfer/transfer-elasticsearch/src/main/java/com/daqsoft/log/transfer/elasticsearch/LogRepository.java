package com.daqsoft.log.transfer.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by ShawnShoper on 2017/5/27.
 */
public interface LogRepository extends ElasticsearchRepository<LogIndex,String> {
}
