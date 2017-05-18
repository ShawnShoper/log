package com.daqsoft.log.util.appender;

import com.daqsoft.commons.feign.support.SpringMvcFeign;
import com.daqsoft.commons.responseEntity.DataResponse;
import com.daqsoft.log.api.KafkaConfigApi;
import com.daqsoft.log.util.Log;
import com.daqsoft.log.util.config.LogProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ShawnShoper on 2017/5/16.
 * Kafka输出实现,内置提供双端队列,进行存储发送失败的日志列表,以便重发,并存储到文件,以便系统挂掉后,日志丢失
 */
public class KafkaAppender extends Appender {
    KafkaProducer<String, Log> producer;
    private String topic;

    public KafkaAppender(LogProperties logProperties) {
        super(logProperties);
    }

    private void initConnect() {
        KafkaConfigApi target = SpringMvcFeign.target(KafkaConfigApi.class, "http://192.168.2.56:8080");
        //初始化kafka...
        DataResponse<Map<String, Object>> kafkaConfig = target.getKafkaConfig(logProperties.getApplication());
        Map<String, Object> data = kafkaConfig.getData();
        this.producer = new KafkaProducer<>(data);
        this.topic = String.valueOf(data.get("topic").toString());
    }

    @Override
    public void write(Log log) throws IOException {
        send(log);
    }

    private void send(Log log) {
        ProducerRecord<String, Log> shawnshoperTest = new ProducerRecord<>(topic, log);
        try {
            producer.send(shawnshoperTest);
        } catch (Exception e) {
            failedQueue.add(log);
        }
    }

    @Override
    public void destroy() {
        if (Objects.nonNull(producer))
            producer.close();
        this.topic = null;
    }

    private LinkedBlockingDeque<Log> failedQueue = new LinkedBlockingDeque();

    @Override
    public void init() {
        initConnect();
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        //初始化线程去解决失败数据
        new Thread(() -> {
            for (; !Thread.currentThread().isInterrupted(); )
                try {
                    //TODO 检查持久化到日志文件的失败文件,写入.
                    if (!atomicBoolean.get() && !failedQueue.isEmpty()) {
                        Log log = failedQueue.poll(5, TimeUnit.SECONDS);
                        if (Objects.nonNull(log))
                            send(log);
                    } else
                        TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    break;
                }
        }).start();
    }
}
