package com.daqsoft.log.collcetor.kafka;

import com.daqsoft.log.collcetor.CollcetorQueue;
import com.daqsoft.log.core.serialize.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by ShawnShoper on 2017/5/25.
 */
@Component
public class KafkaConsumer {
    ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(group = "log-collcetor1",id = "log", topicPartitions = {@TopicPartition(topic = "test",
            partitionOffsets = @PartitionOffset(partition = "0", relativeToCurrent = "true",initialOffset = "0"))})
    public void collector(String logMsg) {
        try {
            Log log = mapper.readValue(logMsg, Log.class);
            System.out.println(log);
            while (!CollcetorQueue.collcetorQueue.offer(log, 5, TimeUnit.SECONDS)) ;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
