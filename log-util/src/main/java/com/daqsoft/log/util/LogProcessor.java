package com.daqsoft.log.util;

import com.daqsoft.log.util.annotation.LogModel;
import com.daqsoft.log.util.appender.Appender;
import com.daqsoft.log.util.config.LogProperties;
import com.daqsoft.log.util.constans.Constans;
import com.daqsoft.log.util.queue.LogQueue;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by ShawnShoper on 2017/4/18.
 * 日志处理类
 */
public class LogProcessor {
    private static int pid = Integer.valueOf(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
    List<Appender> appenders = new ArrayList<>();

    public LogProcessor(Appender... appenders) {
        Arrays.stream(appenders).forEach(this.appenders::add);
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
            for (; !Thread.currentThread().isInterrupted(); ) {
                try {
                    Log log = LogQueue.logQueue.poll(1, TimeUnit.SECONDS);
                    if (Objects.nonNull(log)) {
                        appenders.stream().forEach(e->{
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

    public void processor(final String logMsg, String logLevel, final Class<?> clazz) {
        StackTraceElement lastCall = getLastInvokStack(clazz);
        assembly(logMsg, logLevel, clazz, lastCall);
    }

    private void assembly(final String logMsg, final String logLevel, final Class<?> clazz, final StackTraceElement lastCall) {
        try {
            String methodName = lastCall.getMethodName();
            String className = lastCall.getClassName().toString();
            String model = null;
            Optional<Method> first = Arrays.stream(Test.class.getDeclaredMethods()).filter(e -> e.getName().equals(methodName)).findFirst();
            if (first.isPresent()) {
                Method te = first.get();
                LogModel annotation = te.getAnnotation(LogModel.class);
                if (Objects.nonNull(annotation))
                    model = annotation.value();
            } else {
                LogModel classLogModel = clazz.getAnnotation(LogModel.class);
                if (Objects.nonNull(classLogModel))
                    model = classLogModel.value();
            }
            LogProperties logConfig = LogFactory.getLogProperties();
            Log log = new Log();
            log.setSource(logConfig.getHost() + ":" + logConfig.getPort());
            log.setTime(System.currentTimeMillis());
            log.setContentType(Constans.TYPE_STRING);
            log.setApplication(logConfig.getApplication());
            log.setLineNumber(lastCall.getLineNumber());
            log.setMethodName(methodName);
            log.setClassName(className);
            log.setPid(pid);
            Business business = new Business();
            business.setModel(model);
            business.setContent(logMsg);
            business.setLevel(logLevel);
            log.setBusiness(business);
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
        Optional<StackTraceElement> first = Arrays.stream(Thread.currentThread().getStackTrace()).filter(e -> e.getClassName().equals(clazz.getName())
        ).findFirst();
        return first.isPresent() ? first.get() : null;
    }
}
