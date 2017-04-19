package com.daqsoft.log.util;

import com.daqsoft.commons.core.DateUtil;
import com.daqsoft.log.util.Appender.Appender;
import com.daqsoft.log.util.annotation.LogModel;
import com.daqsoft.log.util.config.LogProperties;
import com.daqsoft.log.util.constans.Constans;
import com.daqsoft.log.util.queue.LogQueue;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Created by ShawnShoper on 2017/4/18.
 * 日志处理类
 */
public class LogProcessor {
    private static int pid = Integer.valueOf(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
    private static OutputStream print = System.out;
    List<Appender> appenders = new ArrayList<>();

    static {
        new LogConsume().start();
    }

    /**
     * 开启死循环一直监控日志队列
     */
    private static class LogConsume extends Thread {
        public LogConsume() {
            this.setName("log-consume");
            this.setDaemon(false);
        }

        @Override
        public void run() {
            for (; !Thread.currentThread().isInterrupted(); ) {
                try {
                    Map<String, Object> poll = LogQueue.logQueue.poll(10, TimeUnit.SECONDS);

                    if (Objects.nonNull(poll)) {
                        try {
                            print.flush();
                            print.write(String.valueOf(poll.get("logmsg")).getBytes());
                            print.write("\r\n".getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
            LogProperties logConfig = LogFactory.getLogConfig();
            Log log = new Log();
            log.setSource(logConfig.getHost() + ":" + logConfig.getPort());
            log.setTime(System.currentTimeMillis());
            log.setContentType(Constans.TYPE_STRING);
            log.setApplication(logConfig.getApplication());
            Business business = new Business();
            business.setModel(model);
            business.setContent(logMsg);
            business.setLevel(logLevel);
            log.setBusiness(business);
            String time = DateUtil.dateToString("yyyy-MM-dd HH:mm:ss.sss", new Date());
            int lineNumber = lastCall.getLineNumber();
            String logstr = String.format("%-23s  %-5s% 6d --- [%30s]:%-5d %s %s", ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a(time).reset().toString(), logLevel, pid, ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a(methodName).reset().toString(), lineNumber, ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a(className).reset().toString(), logMsg);
            Map<String, Object> map = new HashMap<>();
            map.put("logmsg", logstr);
            map.put("log", log);
            LogQueue.logQueue.offer(map);
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

    public LogProcessor(Appender... appenders) {
        Arrays.stream(appenders).forEach(this.appenders::add);
    }
}
