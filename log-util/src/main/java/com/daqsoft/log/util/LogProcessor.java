package com.daqsoft.log.util;

import com.daqsoft.log.core.config.Constans;
import com.daqsoft.log.core.serialize.Business;
import com.daqsoft.log.core.serialize.Log;
import com.daqsoft.log.util.annotation.Channel;
import com.daqsoft.log.util.annotation.ContentType;
import com.daqsoft.log.util.annotation.LogModel;
import com.daqsoft.log.util.appender.Appender;
import com.daqsoft.log.util.config.LogProperties;
import com.daqsoft.log.util.queue.LogQueue;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by ShawnShoper on 2017/4/18.
 * 日志处理类
 * TODO 这里使用线程池,通过Runtime.getRuntime().addShutdownHook()的方式可以在程序被关闭的时候进行关闭
 */
public class LogProcessor {
    private static int pid = Integer.valueOf(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
    private List<Appender> appenders = new ArrayList<>();

    public LogProcessor(List<Appender> appenders) {
        this.appenders = appenders;
        new LogConsume().start();
    }


    /**
     * 开启死循环一直监控日志队列
     */
    class LogConsume extends Thread {
        public LogConsume() {
            this.setName("log-consume");
            this.setDaemon(true);
        }

        @Override
        public void run() {
            //初始化各个Appender
            appenders.stream().forEach(Appender::init);
            for (; !Thread.currentThread().isInterrupted(); ) {
                try {
                    Log log = LogQueue.logQueue.poll(1, TimeUnit.SECONDS);
                    if (Objects.nonNull(log)) {
                        appenders.stream().forEach(e -> {
                            try {
                                e.write(log);
                            } catch (IOException excep) {
                                excep.printStackTrace();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void processor(String channel,final String logMsg, String logLevel, final Class<?> clazz) {
        StackTraceElement lastCall = getLastInvokStack(clazz);
        assembly(channel,logMsg, logLevel, clazz, lastCall);
    }

    private void assembly(String channel,final String logMsg, final String logLevel, final Class<?> clazz, final StackTraceElement lastCall) {
        try {
            String methodName = lastCall.getMethodName();
            String className = lastCall.getClassName().toString();
            String model = null;
            String contentType = com.daqsoft.log.util.config.ContentType.STR.name();
            Optional<Method> first = Arrays.stream(Class.forName(className).getDeclaredMethods()).filter(e -> e.getName().equals(methodName)).findFirst();
            if (first.isPresent()) {
                Method te = first.get();
                LogModel annotation = te.getAnnotation(LogModel.class);
                if (Objects.nonNull(annotation))
                    model = annotation.value();
                ContentType ct = te.getAnnotation(ContentType.class);
                if (Objects.nonNull(ct))
                    contentType = ct.value().name();
                if(Objects.isNull(channel)) {
                    Channel c = te.getAnnotation(Channel.class);
                    if (Objects.nonNull(c))
                        channel = c.value();
                }

            } else {
                LogModel classLogModel = clazz.getAnnotation(LogModel.class);
                if (Objects.nonNull(classLogModel))
                    model = classLogModel.value();
                ContentType ct = clazz.getAnnotation(ContentType.class);
                if (Objects.nonNull(ct))
                    contentType = ct.value().name();
                if(Objects.isNull(channel)) {
                    Channel c = clazz.getAnnotation(Channel.class);
                    if (Objects.nonNull(c))
                        channel = c.value();
                }
            }
            LogProperties logConfig = LogFactory.getLogProperties();
            Business business = new Business();
            business.setModel(model);
            business.setContent(logMsg);
            business.setLevel(logLevel);
            Log log = new Log(channel,logConfig.getApplication(),System.currentTimeMillis(),contentType,logConfig.getHost(),logConfig.getPort(),pid,className,methodName,lastCall.getLineNumber(),business);
            LogQueue.logQueue.offer(log);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    //LogUtil类方法
//    final List<String> localMethodName = Arrays.stream(Logger.class.getDeclaredMethods()).filter(m -> m.getDeclaringClass().getName().equals(Logger.class.getName())).map(e -> this.getClass().getName() + "." + e.getName()).collect(Collectors.toList());

    /**
     * @author ShawnShoper
     * @date 2016/12/9 0009 14:09
     * 选择调用任务的堆栈
     */
    private static StackTraceElement getLastInvokStack(Class<?> clazz) {
//        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//        StackTraceElement stackTraceElement = null;
//        final AtomicBoolean isEnter = new AtomicBoolean(false);
//        for (StackTraceElement ste : stackTrace) {
//            if (ste.getClassName().equals(Logger.class.getName()))
//                isEnter.set(true);
//            if (isEnter.get() && !localMethodName.contains(ste.getClassName() + "." + ste.getMethodName())) {
//                stackTraceElement = ste;
//                break;
//            }
//        }
        //采用最简单的stream API来做,避免太多繁琐的代码
        Optional<StackTraceElement> first = Arrays.stream(Thread.currentThread().getStackTrace()).filter(e -> e.getClassName().equals(clazz.getName())).findFirst();
        return first.isPresent() ? first.get() : null;
    }
}
