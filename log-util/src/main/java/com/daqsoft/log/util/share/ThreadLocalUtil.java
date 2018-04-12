package com.daqsoft.log.util.share;

import com.daqsoft.commons.core.MD5Util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * MCC util
 */
public class ThreadLocalUtil {

    static SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);

    private ThreadLocalUtil() {

    }

    /**
     * id generate for thread semaphore
     * use thread id  and current time to build a unique id
     */

    public static long generateId() {

        return snowflakeIdWorker.nextId();
    }

    /**
     * mcc According method calling stack chain.
     * condition 1:
     * During the chain.
     * Just compare with the thread id is equal to the last log's thread....
     * condition 2:
     * Other the chain
     * First Step
     * calculate the first stack token value and compare to that before the chain's value of the thread local,
     * if result is true,the chain is not the seem chain.
     */
    public static boolean isNewChain(String firstStackToken, int lineNumber, Method method, StackTraceElement[] now, final Optional<ThreadSemaphore> threadSemaphoreOptional) {
        if (method == null) return true;
        //依然得需要分析stack
        Class<?> aClass = getClass(method);
        String className = aClass.getName();
        String methodName = method.getName();
        if (threadSemaphoreOptional.isPresent()) {
            if (firstStackToken.equals(threadSemaphoreOptional.get().getFirstStackToken())) return true;
            if (Thread.currentThread().getId() != threadSemaphoreOptional.get().getTid()) return false;
            int index = -1;
            for (int i = 0; i < now.length; i++) {
                StackTraceElement stackTraceElement = now[i];
                String cn = stackTraceElement.getClassName();
                int ln = stackTraceElement.getLineNumber();
                String mn = stackTraceElement.getMethodName();
                if (className.equals(cn) && lineNumber == ln && methodName.equals(mn)) {
                    index = i;
                    break;
                }
            }
            if (index != -1 && now.length > index) {
                StackTraceElement stackTraceElement = now[index + 1];
                ThreadSemaphore threadSemaphore = threadSemaphoreOptional.get();
                MethodInfo methodInfo = threadSemaphore.getThreadSemaphores().get(threadSemaphore.getThreadSemaphores().size() - 1);
                Class<?> preClass = getClass(methodInfo.getMethod());
                String cn = preClass.getName();
                String mn = methodInfo.getMethod().getName();
                if (stackTraceElement.getMethodName().equals(mn) && stackTraceElement.getClassName().equals(cn))
                    return false;
            } else
                throw new RuntimeException("Can not use log in LogUtil inner class");
        }
        return true;
    }

    /**
     * 用于自增spanId
     *
     * @return
     */
    public static int incrementSpanIndex() {
        return LogThreadLocal.getThreadSemaphore().get().getSpanIndex().incrementAndGet();
    }

    public static Class<?> getClass(Method method) {
        try {
            Field root = method.getClass().getDeclaredField("clazz");
            root.setAccessible(true);
            return (Class<?>) root.get(method);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String firstStackToken(String str) {
        return MD5Util.getMD5Code(str);
    }
}