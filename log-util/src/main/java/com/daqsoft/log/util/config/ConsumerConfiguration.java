package com.daqsoft.log.util.config;

/**
 * Created by ShawnShoper on 2016/11/11.
 */
public class ConsumerConfiguration {
    private String autoCommitInterval;
    private String autoOffsetReset;
    private String checkCrcs;
    private String clientId;
    private String connectionsMaxIdle;
    private String enableAutoCommit = "false";
    private String fetchMaxWait;
    private String fetchMinBytes;
    private String groupId;
    private String heartbeatInterval;
    private String keyDeserializer;
    private String maxPartitionFetchBytes;
    private String metadataMaxAge;
    private String metricReporters;
    private String metricsNumSamples;
    private String metricsSampleWindow;
    private String partitionAssignmentStrategy;
    private String receiveBufferBytes;
    private String reconnectBackOff;
    private String requestTimeout;
    private String retryBackOff;
    private String sendBufferBytes;
    private String sessionTimeout;
    private String valueDeserializer;

    public String getAutoCommitInterval() {
        return autoCommitInterval;
    }

    public void setAutoCommitInterval(String autoCommitInterval) {
        this.autoCommitInterval = autoCommitInterval;
    }

    public String getAutoOffsetReset() {
        return autoOffsetReset;
    }

    public void setAutoOffsetReset(String autoOffsetReset) {
        this.autoOffsetReset = autoOffsetReset;
    }

    public String getCheckCrcs() {
        return checkCrcs;
    }

    public void setCheckCrcs(String checkCrcs) {
        this.checkCrcs = checkCrcs;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getConnectionsMaxIdle() {
        return connectionsMaxIdle;
    }

    public void setConnectionsMaxIdle(String connectionsMaxIdle) {
        this.connectionsMaxIdle = connectionsMaxIdle;
    }

    public String isEnableAutoCommit() {
        return enableAutoCommit;
    }

    public void setEnableAutoCommit(String enableAutoCommit) {
        this.enableAutoCommit = enableAutoCommit;
    }

    public String getFetchMinBytes() {
        return fetchMinBytes;
    }

    public void setFetchMinBytes(String fetchMinBytes) {
        this.fetchMinBytes = fetchMinBytes;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(String heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public String getKeyDeserializer() {
        return keyDeserializer;
    }

    public void setKeyDeserializer(String keyDeserializer) {
        this.keyDeserializer = keyDeserializer;
    }

    public String getMaxPartitionFetchBytes() {
        return maxPartitionFetchBytes;
    }

    public void setMaxPartitionFetchBytes(String maxPartitionFetchBytes) {
        this.maxPartitionFetchBytes = maxPartitionFetchBytes;
    }

    public String getMetadataMaxAge() {
        return metadataMaxAge;
    }

    public void setMetadataMaxAge(String metadataMaxAge) {
        this.metadataMaxAge = metadataMaxAge;
    }

    public String getMetricReporters() {
        return metricReporters;
    }

    public void setMetricReporters(String metricReporters) {
        this.metricReporters = metricReporters;
    }

    public String getMetricsNumSamples() {
        return metricsNumSamples;
    }

    public void setMetricsNumSamples(String metricsNumSamples) {
        this.metricsNumSamples = metricsNumSamples;
    }

    public String getPartitionAssignmentStrategy() {
        return partitionAssignmentStrategy;
    }

    public void setPartitionAssignmentStrategy(String partitionAssignmentStrategy) {
        this.partitionAssignmentStrategy = partitionAssignmentStrategy;
    }

    public String getReceiveBufferBytes() {
        return receiveBufferBytes;
    }

    public void setReceiveBufferBytes(String receiveBufferBytes) {
        this.receiveBufferBytes = receiveBufferBytes;
    }

    public String getReconnectBackOff() {
        return reconnectBackOff;
    }

    public void setReconnectBackOff(String reconnectBackOff) {
        this.reconnectBackOff = reconnectBackOff;
    }

    public String getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(String requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public String getRetryBackOff() {
        return retryBackOff;
    }

    public void setRetryBackOff(String retryBackOff) {
        this.retryBackOff = retryBackOff;
    }

    public String getSendBufferBytes() {
        return sendBufferBytes;
    }

    public void setSendBufferBytes(String sendBufferBytes) {
        this.sendBufferBytes = sendBufferBytes;
    }

    public String getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(String sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public String getValueDeserializer() {
        return valueDeserializer;
    }

    public void setValueDeserializer(String valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
    }

    public String getFetchMaxWait() {
        return fetchMaxWait;
    }

    public void setFetchMaxWait(String fetchMaxWait) {
        this.fetchMaxWait = fetchMaxWait;
    }

    public String getMetricsSampleWindow() {
        return metricsSampleWindow;
    }

    public void setMetricsSampleWindow(String metricsSampleWindow) {
        this.metricsSampleWindow = metricsSampleWindow;
    }
}
