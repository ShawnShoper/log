package com.daqsoft.log.util;

import com.daqsoft.commons.core.DateUtil;
import com.daqsoft.log.util.config.LogProperties;
import com.daqsoft.log.util.constans.Constans;
import com.daqsoft.log.util.queue.LogQueue;
import org.fusesource.jansi.Ansi;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class Logger {
    private Class<?> clazz;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Logger(Class<?> clazz) {
        this.clazz = clazz;
    }

    private LogProperties logProperties;

    @PreDestroy
    public void destroy() {
    }

    private static int pid = Integer.valueOf(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);

    private static OutputStream print = System.out;

    public void info(String logMsg, Log log) {
        log(logMsg, Constans.INFO, log);
    }

    public void info(String formatter, Log log, Object... obj) {
        info(String.format(formatter, obj), log);
    }

    public void warn(String logMsg, Log log) {
        log(logMsg, Constans.WARN, log);
    }

    public void warn(String formatter, Log log, Object... obj) {
        warn(String.format(formatter, obj), log);
    }

    private void debug(String logMsg, Log log) {
        log(logMsg, Constans.DEBUG, log);
    }

    public void debug(String formatter, Log log, Object... obj) {
        debug(String.format(formatter, obj), log);
    }

    public void error(String logMsg, Log log) {
        log(ansi().eraseScreen().fg(Ansi.Color.RED).a(logMsg).reset().toString(), Constans.ERROR, log);
    }

    public void error(String formatter, Log log, Object... obj) {
        error(String.format(formatter, obj), log);
    }

    /**
     * @author ShawnShoper
     * @date 2016/12/20 0020 11:27
     * LogUtil构造，构造中开启线程进行维护
     */
    public Logger(LogProperties logProperties) {
        this.logProperties = logProperties;
    }

    /**
     * @author ShawnShoper
     * @date 2016/12/20 0020 16:35
     * 记录日志
     */
    private void log(String logMsg, String logLevel, Log log) {
        StackTraceElement lastCall = choseLastInvokStack();
        String time = DateUtil.dateToString("yyyy-MM-dd HH:mm:ss.sss", new Date());
        String methodName = lastCall.getMethodName().toString();
        String className = lastCall.getClassName().toString();
        int lineNumber = lastCall.getLineNumber();
        String logstr = String.format("%-23s  %-5s% 6d --- [%30s]:%-5d %s %s", ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a(time).reset().toString(), logLevel, pid, ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a(methodName).reset().toString(), lineNumber, ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a(className).reset().toString(), logMsg);
        try {
            print.write(logstr.getBytes());
            print.write("\r\n".getBytes());
            print.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogQueue.logQueue.offer(log);
    }

    //LogUtil类方法
    final List<String> localMethodName = Arrays.stream(Logger.class.getDeclaredMethods()).filter(m -> m.getDeclaringClass().getName().equals(Logger.class.getName())).map(e -> this.getClass().getName() + "." + e.getName()).collect(Collectors.toList());

    /**
     * @author ShawnShoper
     * @date 2016/12/9 0009 14:09
     * 选择调用任务的堆栈
     */
    private StackTraceElement choseLastInvokStack() {
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