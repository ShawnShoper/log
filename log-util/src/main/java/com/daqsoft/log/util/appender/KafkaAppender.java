package com.daqsoft.log.util.appender;

import com.daqsoft.commons.feign.support.SpringMvcFeign;
import com.daqsoft.commons.responseEntity.DataResponse;
import com.daqsoft.log.api.KafkaConfigApi;
import com.daqsoft.log.core.serialize.Log;
import com.daqsoft.log.util.config.KafkaProperties;
import com.daqsoft.log.util.config.LogProperties;
import com.daqsoft.log.util.exception.KafkaConnectionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ShawnShoper on 2017/5/16.
 * Kafka输出实现,内置提供双端队列,进行存储发送失败的日志列表,以便重发,并存储到文件,以便系统挂掉后,日志丢失
 */
public class KafkaAppender extends Appender {
    KafkaProducer<String, String> producer;
    private String topic;
    //kid is key's is ,use to kafka partition
    private String kid;
    //检查服务器状态是否可用
    private AtomicBoolean available = new AtomicBoolean(false);
    //Failed queue
//    private LinkedBlockingDeque<Log> failedQueue = new LinkedBlockingDeque();
    private KafkaProperties kafka;

    public KafkaAppender(LogProperties logProperties) {
        super(logProperties);
        kafka = logProperties.getKafka();
    }

    private void initConnect() {
        KafkaConfigApi target = SpringMvcFeign.target(KafkaConfigApi.class, "http://" + kafka.getKafkaServer());
        //Init kafka...
        DataResponse<Map<String, Object>> kafkaConfig = target.getKafkaConfig(kafka.getKafkaKey(), kafka.getKafkaCert());
        kid = kafka.getKafkaKey();
        if (kafkaConfig.getCode() == 0) {
            Map<String, Object> data = kafkaConfig.getData();
            this.producer = new KafkaProducer<>(data);
            if (Objects.isNull(data.get("topic")))
                throw new RuntimeException("this topic not exists in server config");
            this.topic = data.get("topic").toString();
            available = new AtomicBoolean(true);
        } else {
            throw new KafkaConnectionException(kafkaConfig.getMessage());
        }
    }

    @Override
    public void write(Log log) throws IOException {
        send(log);
    }

    private AtomicBoolean over = new AtomicBoolean(true);

    ObjectMapper mapper = new ObjectMapper();

    private void send(Log log) {
        over.compareAndSet(true, false);
        try {
            String json = mapper.writeValueAsString(log);
            try {
                //Kafka key
                if (available.get()) {
                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(Objects.isNull(log.getChannel()) ? this.topic : log.getChannel(), 0, kid, json);
                    producer.send(producerRecord);
                    producer.flush();
                }
//            else failedQueue.add(log);
            } catch (Exception e) {
                e.printStackTrace();
//            failedQueue.add(log);
                disConnect();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            over.compareAndSet(false, true);
        }
    }

    @Override
    public boolean canDestory() {
        return over.get();
    }

    /**
     * Destroy Kafka produce connection
     * and failed output stream
     */
    @Override
    public void destroy() {
        if (Objects.nonNull(producer)) {
            try {
                producer.close();
            } catch (Throwable ex) {
                ex.printStackTrace();
            } finally {
                this.topic = null;
            }
        }
//        if (Objects.nonNull(backupOutputWrite)) {
//            try {
//                backupOutputWrite.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                backupOutputWrite = null;
//            }
//        }
    }


    /**
     * 初始化连接信息并创建连接失败处理以及重连机制
     */
    @Override
    public void init() {
        initConnect();
        //初始化线程去解决失败数据
        //失败队列写入文件..
        //TODO 先注释掉一下模块,错误日志备份策略先走FileAppender
//        registyFailedHandle();
        //程序启动后连接不健康失败情况下,检测kafka终端是否健康,并重新创建连接
        registryConnectionChecker();
    }

    /**
     * Registry kafka connection checker
     * if kafka connection break then reconnect
     */
    private void registryConnectionChecker() {
        Thread reconnect = new Thread(() -> {
            for (; !Thread.currentThread().isInterrupted(); ) {
                try {
                    if (!available.get())
                        initConnect();
                    else
                        TimeUnit.SECONDS.sleep(10);
                } catch (Exception e) {
                    if (e instanceof InterruptedException)
                        Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        });
        reconnect.setName("log-kafka-appender-reconnect");
        reconnect.start();
    }

    /**
     * Close kafka produce connection and set produce as null
     */

    private void disConnect() {
        available.set(false);
        try {
            if (Objects.nonNull(producer))
                producer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer = null;
        }
    }
   /* private BufferedWriter backupOutputWrite;

    //    private OutputStream outputStream;
    private void registyFailedHandle() {
        Thread handleFaildLog = new Thread(() -> {
            String kafkaBackDir = kafka.getKafkaBackDir();
            File dir = new File(kafkaBackDir);
            OutputStreamWriter backupWrite = null;
            String fileName = dir + File.separator + System.currentTimeMillis();
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    throw new RuntimeException("Can not create dir ['" + dir + "'] ,maybe your current user no permission or has being used");
                }
            }
            for (; !Thread.currentThread().isInterrupted(); ) {
                try {
                    //TODO 检查持久化到日志文件的失败文件,写入.
                    //检查
                    if (!available.get()) {
                        if (!failedQueue.isEmpty()) {
                            if (Objects.isNull(backupOutputWrite)) {
                                try {
                                    backupWrite = new OutputStreamWriter(new FileOutputStream(new File(fileName),true));
                                    backupOutputWrite = new BufferedWriter(backupWrite);
//                    outputStream = new FileOutputStream(new File(logProperties.getKafkaBackDir()));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            Log log = failedQueue.poll(5, TimeUnit.SECONDS);
                            if (Objects.nonNull(log)) {
                                try {
                                    backupOutputWrite.write(parseLog(log));
                                    backupOutputWrite.flush();
                                } catch (Exception e) {
                                    failedQueue.add(log);
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            if (Objects.nonNull(backupWrite)) {
                                try {
                                    backupWrite.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }finally {
                                    backupWrite = null;
                                }
                            }
                            if (Objects.nonNull(backupOutputWrite))
                                try {
                                    backupOutputWrite.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }finally {
                                    backupOutputWrite = null;
                                }
                            fileName = dir + File.separator + System.currentTimeMillis();
                            TimeUnit.SECONDS.sleep(10);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        handleFaildLog.setName("log-kafka-appender-failed-log-handle");
        handleFaildLog.start();
    }

    private static final String INTERVALSYMBOL = "\001";

    protected String parseLog(Log log) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Objects.nonNull(log.getApplication()) ? log.getApplication() : "" + INTERVALSYMBOL);
        stringBuilder.append(Objects.nonNull(log.getPid()) ? log.getPid() : "" + INTERVALSYMBOL);
        stringBuilder.append(Objects.nonNull(log.getContentType()) ? log.getContentType() : "" + INTERVALSYMBOL);
        stringBuilder.append(Objects.nonNull(log.getClassName()) ? log.getClassName() : "" + INTERVALSYMBOL);
        stringBuilder.append(Objects.nonNull(log.getMethodName()) ? log.getMethodName() : "" + INTERVALSYMBOL);
        stringBuilder.append(Objects.nonNull(log.getLineNumber()) ? log.getLineNumber() : "" + INTERVALSYMBOL);
        stringBuilder.append(Objects.nonNull(log.getSource()) ? log.getSource() : "" + INTERVALSYMBOL);
        stringBuilder.append(Objects.nonNull(log.getTime()) ? log.getTime() : "" + INTERVALSYMBOL);
        Business business = log.getBusiness();
        stringBuilder.append(Objects.nonNull(business.getLevel()) ? business.getLevel() : "" + INTERVALSYMBOL);
        stringBuilder.append(Objects.nonNull(business.getModel()) ? business.getModel() : "" + INTERVALSYMBOL);
        stringBuilder.append(Objects.nonNull(business.getVia()) ? business.getVia() : "" + INTERVALSYMBOL);
        stringBuilder.append(Objects.nonNull(business.getContent()) ? business.getContent() : "");
        return stringBuilder.toString() + "\r\n";
    }

    *//*

    private void writeErrorInfo(String dir, String fname, int line) {
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(dir + File.separator + Constans.PROPERTY));
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(Constans.FNAME + "=" + fname);
            bufferedWriter.write("\r\n");
            bufferedWriter.write(Constans.LINE + "=" + line);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(fileWriter))
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (Objects.nonNull(bufferedWriter))
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    }*/
}
