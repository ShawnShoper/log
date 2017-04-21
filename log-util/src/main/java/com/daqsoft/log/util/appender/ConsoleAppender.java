package com.daqsoft.log.util.appender;

import com.daqsoft.commons.core.DateUtil;
import com.daqsoft.commons.core.StringUtil;
import com.daqsoft.log.util.Log;
import com.daqsoft.log.util.config.LogProperties;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Created by ShawnShoper on 2017/4/19.
 */
public class ConsoleAppender extends Appender {
    PrintStream print = System.out;
    String pattern;
    List<LogPattern> logPatterns;

    public ConsoleAppender(LogProperties logProperties) {
        super(logProperties);
    }

    @Override
    public void init() {
        logPatterns = new ArrayList<>();
        String partten = logProperties.getPartten();
        String[] log_partten = partten.split("%");
        Pattern reg_pattern = Pattern.compile("(-)?(\\d*?)(\\{.*?\\})?([a-z]+)", Pattern.CASE_INSENSITIVE);
        //遍历pattern
        Arrays.stream(log_partten).map(String::trim).filter(StringUtil::nonEmpty).map(e -> {
            Matcher matcher = reg_pattern.matcher(e);
            if (matcher.find()) {
                String neg = matcher.group(1);
                if (Objects.nonNull(neg) && !"-".equals(neg))
                    throw new RuntimeException(String.format("Log express neg %s not support", neg));
                String offset = matcher.group(2);
                String pattern = matcher.group(3);
                if (Objects.nonNull(pattern)) {
                    pattern = pattern.substring(1, pattern.length() - 1);
                }
                String name = matcher.group(4);
                if (Objects.isNull(name))
                    throw new RuntimeException(String.format("Log express tag %s not support", name));
                Tag tag;
                try {
                    tag = Tag.valueOf(name.toUpperCase());
                } catch (IllegalArgumentException e1) {
                    throw new RuntimeException(String.format("Log express tag %s not support", name));
                }
                return new LogPattern(tag.getName(), offset.isEmpty() ? 0 : Integer.valueOf(offset), pattern, (Objects.isNull(neg) ? ' ' : '-'));
            } else {
                return null;
            }
        }).forEach(e -> {
            System.out.println(e);
            System.out.println(logPatterns);
            logPatterns.add(e);
        });
    }

    /**
     * 日志写入
     *
     * @param log
     * @throws IOException
     */
    @Override
    public void write(Log log) throws IOException {
        try {
            StringBuilder logMsg = new StringBuilder();
            if (!logPatterns.isEmpty())
                logPatterns.stream().forEach(e -> {
                    String name = e.getName();
                    String tmp = "";
                    if (Tag.T.name.equals(name)) {
                        String time = DateUtil.dateToString(e.getPattern(), new Date(log.getTime()));
                        tmp = time;
                    } else if (Tag.C.name.equals(name)) {
                        tmp = log.getBusiness().getContent();
                    } else if (Tag.L.name.equals(name)) {
                        tmp = log.getBusiness().getLevel();
                    } else if (Tag.P.name.equals(name)) {
                        tmp = String.valueOf(log.getPid());
                    } else if (Tag.MN.name.equals(name)) {
                        tmp = "[" + String.valueOf(log.getMethodName()) + "]";
                    } else if (Tag.LN.name.equals(name)) {
                        tmp = "-" + String.valueOf(log.getLineNumber());
                    } else if (Tag.CN.name.equals(name)) {
                        tmp = String.valueOf(log.getClassName());
                    }
                    System.out.println("%" + (' ' == e.getNeg() ? "" : e.getNeg()) + e.getOffset() + "s");
                    tmp = String.format("%" + (' ' == e.getNeg() ? "" : e.getNeg()) + (e.getOffset() == 0 ? "" : e.getOffset()) + "s", tmp);
                    logMsg.append(tmp);
                });
            System.out.println(logMsg.toString());
            String time = DateUtil.dateToString("yyyy-MM-dd HH:mm:ss.sss", new Date(log.getTime()));
            String logstr = String.format("%-23s%-5s%6d%30s%-5d %s %s", ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a(time).reset().toString(), log.getBusiness().getLevel(), log.getPid(), ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a(log.getMethodName()).reset().toString(), log.getLineNumber(), ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a(log.getClassName()).reset().toString(), log.getBusiness().getContent());
//            String logstr = String.format("%-23s%-5s%6d%30s%-5d%s%s",time, log.getBusiness().getLevel(), log.getPid(), log.getMethodName(), log.getLineNumber(), log.getClassName(), log.getBusiness().getContent());
//            %-23s%-5s%6d%30s%-5d %s %s
            print.flush();
            print.write(logstr.getBytes());
            print.write("\r\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(String.format("%2s", String.valueOf(3)));
    }

    enum Tag {
        T("t", 23), L("l", 5), P("p", 6), MN("mn", 30), LN("ln", -5), CN("cn", 30), C("c", -1);

        Tag(String name, int offset) {
            this.name = name;
            this.offset = offset;
        }

        String name;
        int offset;

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 日志模板
     * %t   时间
     * %l   日志登记
     * %p   pid
     * %mn  方法名
     * %ln  代码所在行号
     * %cn  类名
     * %-5[yyyy-MM-dd HH:mm:ss.ssss]t %-5l %6p %30mn %5ln %5cn %5c
     */
    class LogPattern {
        private String name;
        //偏移量
        private int offset;
        private String pattern;
        //'-'或者''
        private char neg;

        public String getName() {
            return name;
        }

        public LogPattern(String name, int offset, String pattern, char neg) {
            this.name = name;
            this.offset = offset;
            this.pattern = pattern;
            this.neg = neg;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public char getNeg() {
            return neg;
        }

        public void setNeg(char neg) {
            this.neg = neg;
        }
    }
}
