package com.daqsoft.log.util.config;

/**
 * Created by ShawnShoper on 2016/11/11.
 */
public class KafkaProperties {
    private ConsumerConfiguration consumer;
    private ProducerConfiguration producer;
    private String bootstrapServers;
    private String zookeeperConnect;

    public ConsumerConfiguration getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerConfiguration consumer) {
        this.consumer = consumer;
    }

    public ProducerConfiguration getProducer() {
        return producer;
    }

    public void setProducer(ProducerConfiguration producer) {
        this.producer = producer;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getZookeeperConnect() {
        return zookeeperConnect;
    }

    public void setZookeeperConnect(String zookeeperConnect) {
        this.zookeeperConnect = zookeeperConnect;
    }

}
