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
import com.daqsoft.log.util.share.*;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ShawnShoper on 2017/4/18.
 * 日志处理类
 * TODO 这里使用线程池,通过Runtime.getRuntime().addShutdownHook()的方式可以在程序被关闭的时候进行关闭
 */
public class LogProcessor {
    private static int pid = Integer.valueOf(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
    private List<Appender> appenders = new ArrayList<>();

    /**
     * 关闭整个日志环境.
     *
     * @return
     * @throws InterruptedException
     */
    protected synchronized void shutdown() throws InterruptedException {
        for (; ; ) {
            boolean canShutdown = false;
            if (remain.get() == 0 && LogQueue.logQueue.isEmpty()) {
                for (Appender appender : this.appenders) {
                    boolean b = appender.canDestory();
                    if (b) {
                        canShutdown = true;
                        break;
                    }
                }
            }
            if (canShutdown) {
                return;
            } else
                TimeUnit.MILLISECONDS.sleep(10);
        }
    }

    public LogProcessor(List<Appender> appenders) {
        this.appenders = appenders;
        new LogConsume().start();
        //注册钩子,避免程序结束时,日志并未完全写完.
    }

    AtomicInteger remain = new AtomicInteger(0);

    /**
     * 开启死循环一直监控日志队列
     */
    class LogConsume extends Thread {
        public LogConsume() {
            this.setName("log-consume");
            this.setDaemon(true);
            //初始化各个Appender
            appenders.stream().forEach(Appender::init);
        }

        @Override
        public void run() {
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
                        remain.decrementAndGet();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * 进行日志详细的记录加工
     *
     * @param channel  消息队列topic
     * @param logInfo  日志信息
     * @param logLevel 日志级别
     * @param clazz    日志记录所在类
     */
    public void processor(String channel, final LogInfo logInfo, String logLevel, final Class<?> clazz) {
        StackTraceElement lastCall = getLastInvokStack(clazz);
        Log log = assembly(channel, logInfo, logLevel, clazz, lastCall);
        if (Objects.nonNull(log)) {
            LogQueue.logQueue.offer(log);
            remain.incrementAndGet();
        }
    }


    /**
     * 装配日志内容.
     *
     * @param channel  消息队列topic
     * @param logInfo  日志信息
     * @param logLevel 日志级别
     * @param clazz    日志记录所在类
     * @param lastCall 最后调用栈信息
     * @return
     */
    private Log assembly(String channel, final LogInfo logInfo, final String logLevel, final Class<?> clazz, final StackTraceElement lastCall) {
        Log log = null;
        try {
            String methodName = lastCall.getMethodName();
            String className = lastCall.getClassName();
            String model = null;
            String contentType = com.daqsoft.log.util.config.ContentType.STR.name();
            //record mcc
            Optional<ThreadSemaphore> threadSemaphoreOptional = LogThreadLocal.getThreadSemaphore();
            int recordNumber = lastCall.getLineNumber();
            //Get method name
            Optional<Method> methodOptional = Arrays.stream(Class.forName(className).getDeclaredMethods()).filter(e -> e.getName().equals(methodName)).findFirst();
            String firstStackToken = ThreadLocalUtil.firstStackToken(className + methodName + recordNumber);
            boolean isNewChain = false;
            Method method = null;
            ChainResult chainResult = ChainResult.EMPTY;
            if (methodOptional.isPresent())
                method = methodOptional.get();
            if (threadSemaphoreOptional.isPresent()) {
                chainResult = ThreadLocalUtil.analyzeChain(firstStackToken, recordNumber, method, Thread.currentThread().getStackTrace(), threadSemaphoreOptional);
                if (chainResult == ChainResult.NOT_SAME)
                    isNewChain = true;
            } else isNewChain = true;

            if (isNewChain) {
                ThreadSemaphore threadSemaphore = new ThreadSemaphore();
                threadSemaphore.setFirstStackToken(firstStackToken);
                threadSemaphore.setRootMethodName(methodName);
                threadSemaphore.setRootClassName(className);
                threadSemaphore.setRootLineNumber(recordNumber);
                threadSemaphore.setTid(Thread.currentThread().getId());
                MethodInfo methodInfo = new MethodInfo(method, Thread.currentThread().getStackTrace(), recordNumber);
                threadSemaphore.getThreadSemaphores().add(methodInfo);
                LogThreadLocal.setThreadSemaphore(threadSemaphore);
            } else {
                ThreadLocalUtil.incrementSpanIndex();
                ThreadSemaphore threadSemaphore = threadSemaphoreOptional.get();
                MethodInfo methodInfo = new MethodInfo(method, Thread.currentThread().getStackTrace(), recordNumber);
                if (chainResult == ChainResult.SAME_SUBTHREAD)
                    //开始产生分支.
                    methodInfo.setSpanIndex(threadSemaphore.getSpanIndex().incrementAndGet());
                threadSemaphore.getThreadSemaphores().add(methodInfo);
            }
//            System.out.println(logInfo.getMsg() + "\t" + LogThreadLocal.getThreadSemaphore().get().getFirstStackToken() + " transaction id " + LogThreadLocal.getThreadSemaphore().get().getId());
            GenericDeclaration executable;
            if (Objects.nonNull(method))
                executable = method;
            else
                executable = clazz;
            LogModel annotation = executable.getAnnotation(LogModel.class);
            if (Objects.nonNull(annotation))
                model = annotation.value();
            ContentType ct = executable.getAnnotation(ContentType.class);
            if (Objects.nonNull(ct))
                contentType = ct.value().name();
            if (Objects.isNull(channel)) {
                Channel c = executable.getAnnotation(Channel.class);
                if (Objects.nonNull(c))
                    channel = c.value();
            }
            LogProperties logConfig = LogFactory.getLogProperties();
            Business business = new Business();
            business.setModel(model);
            Throwable throwable = logInfo.getThrowable();
            String logMsg = logInfo.getMsg();
            if (Objects.nonNull(throwable)) {
                //redirect the default throwable stream
                InnerPrintStream printStream = new InnerPrintStream(new InnerOutPutStream());
                throwable.printStackTrace(printStream);
                printStream.close();
                logMsg += Constans.NEWLINE + printStream.getMessage();
            }
            business.setContent(logMsg);
            business.setLevel(logLevel);
            ThreadSemaphore threadSemaphore = LogThreadLocal.getThreadSemaphore().get();
            log = new Log(channel, logConfig.getApplication(), System.currentTimeMillis(), contentType, logConfig.getHost(), logConfig.getPort(), pid, className, methodName, recordNumber, business,Thread.currentThread().getId(),threadSemaphore.getSpanIndex().get(),threadSemaphore.getId());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return log;
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
