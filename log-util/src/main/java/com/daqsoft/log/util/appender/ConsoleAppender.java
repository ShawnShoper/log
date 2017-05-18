package com.daqsoft.log.util.appender;

import com.daqsoft.commons.core.DateUtil;
import com.daqsoft.log.util.Log;
import com.daqsoft.log.util.config.LogPattern;
import com.daqsoft.log.util.config.LogProperties;
import com.daqsoft.log.util.config.Tag;
import com.daqsoft.log.util.constans.Constans;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Created by ShawnShoper on 2017/4/19.
 * 控制台输出Appender
 */
public class ConsoleAppender extends Appender {
    PrintStream print = System.out;
    List<LogPattern> logPatterns;
    public final static String PERCENT = "%";

    public ConsoleAppender(LogProperties logProperties, List<LogPattern> logPatterns) {
        super(logProperties);
        this.logPatterns = logPatterns;
    }

    @Override
    public void init() {
//        logPatterns = new ArrayList<>();
//        String partten = logProperties.getPartten();
//        String[] log_partten = partten.split(PERCENT);
//        Pattern reg_pattern = Pattern.compile("(-)?(\\d*?)(\\{.*?\\})?([a-z]+)", Pattern.CASE_INSENSITIVE);
//        //遍历pattern
//        Arrays.stream(log_partten).map(String::trim).filter(StringUtil::nonEmpty).map(e -> {
//            Matcher matcher = reg_pattern.matcher(e);
//            if (matcher.find()) {
//                String neg = matcher.group(1);
//                if (Objects.nonNull(neg) && !"-".equals(neg))
//                    throw new RuntimeException(String.format("Log express neg %s not support", neg));
//                String offset = matcher.group(asd);
//                String pattern = matcher.group(3);
//                if (Objects.nonNull(pattern)) {
//                    pattern = pattern.substring(1, pattern.length() - 1);
//                }
//                String name = matcher.group(4);
//                if (Objects.isNull(name))
//                    throw new RuntimeException(String.format("Log express tag %s not support", name));
//                Tag tag;
//                try {
//                    tag = Tag.valueOf(name.toUpperCase());
//                } catch (IllegalArgumentException e1) {
//                    throw new RuntimeException(String.format("Log express tag %s not support", name));
//                }
//                return new LogPattern(tag.getName(), offset.isEmpty() ? 0 : Integer.valueOf(offset), pattern, (Objects.isNull(neg) ? ' ' : '-'));
//            } else {
//                return null;
//            }
//        }).forEach(logPatterns::add);

    }

    public Map<String, String> prepare(Log log) {
        Map<String, String> tag_value = new HashMap<>();
//        System.out.println(ansi().eraseScreen().fg(Ansi.Color.RED).a("~~~~~~").reset().toString());
//        System.out.println(ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a("~~~~~~").reset().toString());
//        System.out.println(ansi().eraseScreen().fg(Ansi.Color.CYAN).a("~~~~~~").reset().toString());
//        System.out.println(ansi().eraseScreen().fg(Ansi.Color.BLACK).a("~~~~~~").reset().toString());
//        System.out.println(ansi().eraseScreen().fg(Ansi.Color.BLUE).a("~~~~~~").reset().toString());
//        System.out.println(ansi().eraseScreen().fg(Ansi.Color.DEFAULT).a("~~~~~~").reset().toString());
//        System.out.println(ansi().eraseScreen().fg(Ansi.Color.GREEN).a("~~~~~~").reset().toString());
//        System.out.println(ansi().eraseScreen().fg(Ansi.Color.WHITE).a("~~~~~~").reset().toString());
//        System.out.println(ansi().eraseScreen().fg(Ansi.Color.YELLOW).a("~~~~~~").reset().toString());

        if (!logPatterns.isEmpty())
            logPatterns.stream().forEach(e -> {
                String name = e.getName();
                String tmp = "";

                if (Tag.T.name.equals(name)) {
                    String time = DateUtil.dateToString(e.getPattern(), new Date(log.getTime()));
                    tmp = time;
                    tmp = String.format("%" + (' ' == e.getNeg() ? "" : e.getNeg()) + (e.getOffset() == 0 ? "" : e.getOffset()) + "s", tmp);
                } else if (Tag.C.name.equals(name)) {
                    tmp = log.getBusiness().getContent();
                    tmp = String.format("%" + (' ' == e.getNeg() ? "" : e.getNeg()) + (e.getOffset() == 0 ? "" : e.getOffset()) + "s", tmp);
                } else if (Tag.L.name.equals(name)) {
                    String tag_name = log.getBusiness().getLevel();
                    tmp = tag_name;
                    if (tmp.length() < 5) {
                        int length = tmp.length();
                        int i = Tag.L.offset - length;
                        String m = "";
                        for (int k = 0; k < i; k++)
                            m += " ";
                        tmp = m + tmp;
                    }
                    if (tag_name == Constans.DEBUG)
                        tmp = ansi().eraseScreen().fg(Ansi.Color.BLUE).a(tmp).reset().eraseScreen().toString();
                    else if (tag_name == Constans.ERROR)
                        tmp = ansi().eraseScreen().fg(Ansi.Color.RED).a(tmp).reset().toString();
                    else if (tag_name == Constans.INFO)
                        tmp = ansi().eraseScreen().fg(Ansi.Color.GREEN).a(tmp).reset().eraseScreen().toString();
                    else if (tag_name == Constans.WARN)
                        tmp = ansi().eraseScreen().fg(Ansi.Color.YELLOW).a(tmp).reset().eraseScreen().toString();
                    tmp = String.format("%" + (' ' == e.getNeg() ? "" : e.getNeg()) + (e.getOffset() == 0 ? "" : e.getOffset()) + "s", tmp);
                } else if (Tag.P.name.equals(name)) {
                    tmp = String.valueOf(log.getPid());
                    tmp = String.format("%" + (' ' == e.getNeg() ? "" : e.getNeg()) + (e.getOffset() == 0 ? "" : e.getOffset() + 11) + "s", ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a(tmp).reset().toString());
                } else if (Tag.MN.name.equals(name)) {
                    tmp = String.valueOf(log.getMethodName());
                    tmp = String.format("%" + (' ' == e.getNeg() ? "" : e.getNeg()) + (e.getOffset() == 0 ? "" : e.getOffset()) + "s", tmp);
                } else if (Tag.LN.name.equals(name)) {
                    tmp = String.valueOf(log.getLineNumber());
//                    tmp = String.format("%" + (' ' == e.getNeg() ? "" : e.getNeg()) + (e.getOffset() == 0 ? "" : e.getOffset()) + "s", tmp);
                } else if (Tag.CN.name.equals(name)) {
                    tmp = String.valueOf(log.getClassName());
                    if (e.getOffset() < 0 || Math.abs(Tag.CN.offset) < tmp.length()) {
                        String[] split = tmp.split("\\.");
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < split.length - 1; i++)
                            stringBuilder.append(split[i].charAt(1) + ".");
                        tmp = stringBuilder.toString() + split[split.length - 1];
//                        tmp = tmp.substring(0, tmp.length() + Math.abs(tag.getOffset()) - tmp.length());
                    }
                    tmp = String.format("%" + (' ' == e.getNeg() ? "" : e.getNeg()) + (e.getOffset() == 0 ? "" : e.getOffset() + 11) + "s", ansi().eraseScreen().fg(Ansi.Color.CYAN).a(tmp).reset().toString());
                }


                tag_value.put(PERCENT + (' ' == e.getNeg() ? "" : e.getNeg()) + (e.getOffset() == 0 ? "" : e.getOffset()) + (Objects.nonNull(e.getPattern()) ? "{" + e.getPattern() + "}" : "") + e.getName(), tmp);
            });
        return tag_value;
    }

    public void write(Log log) throws IOException {
        Map<String, String> prepare = prepare(log);
        String partten = logProperties.getPartten();
        String out = partten;
        for (String k : prepare.keySet()) {
            String s = prepare.get(k);
            out = out.replace(k, s);
        }
        print.write(out.getBytes());
        print.write("\r\n".getBytes());
        print.flush();
    }

    @Override
    public void destroy() {
        if (Objects.isNull(print))
            print.close();
    }


//    /**
//     * 日志写入
//     *
//     * @param log
//     * @throws IOException
//     */
//    @Override
//    public void write(Log log) throws IOException {
//        try {
//            StringBuilder logMsg = new StringBuilder();
//            if (!logPatterns.isEmpty())
//                logPatterns.stream().forEach(e -> {
//                    String name = e.getName();
//                    String tmp = "";
//                    Tag tag = null;
//                    if (Tag.T.name.equals(name)) {
//                        tag = Tag.T;
//                        String time = DateUtil.dateToString(e.getPattern(), new Date(log.getTime()));
//                        tmp = time;
//                    } else if (Tag.C.name.equals(name)) {
//                        tag = Tag.C;
//                        tmp = log.getBusiness().getContent();
//                    } else if (Tag.L.name.equals(name)) {
//                        tag = Tag.L;
//                        tmp = log.getBusiness().getLevel();
//                    } else if (Tag.P.name.equals(name)) {
//                        tag = Tag.P;
//                        tmp = String.valueOf(log.getPid());
//                    } else if (Tag.MN.name.equals(name)) {
//                        tag = Tag.MN;
//                        tmp = "[" + String.valueOf(log.getMethodName()) + "]";
//                    } else if (Tag.LN.name.equals(name)) {
//                        tag = Tag.LN;
//                        tmp = "-" + String.valueOf(log.getLineNumber());
//                    } else if (Tag.CN.name.equals(name)) {
//                        tag = Tag.CN;
//                        tmp = String.valueOf(log.getClassName());
//                    }
//                    if (e.getOffset() < 0 && Math.abs(tag.getOffset()) < tmp.length()) {
//                        tmp = tmp.substring(0, tmp.length() + Math.abs(tag.getOffset()) - tmp.length());
//                    }
////                    System.out.println("%" + (' ' == e.getNeg() ? "" : e.getNeg()) + e.getOffset() + "s");
//                    tmp = String.format("%" + (' ' == e.getNeg() ? "" : e.getNeg()) + (e.getOffset() == 0 ? "" : e.getOffset()) + "s", tmp);
//                    tmp+=" ";
//                    System.out.println(tmp);
//                    logMsg.append(tmp);
//                });
////            System.out.println(logMsg.toString());
////            String time = DateUtil.dateToString("yyyy-MM-dd HH:mm:ss.sss", new Date(log.getTime()));
////            String logstr = String.format("%-23s%-5s%6d%30s%-5d %s %s", ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a(time).reset().toString(), log.getBusiness().getLevel(), log.getPid(), ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a(log.getMethodName()).reset().toString(), log.getLineNumber(), ansi().eraseScreen().fg(Ansi.Color.MAGENTA).a(log.getClassName()).reset().toString(), log.getBusiness().getContent());
////            String logstr = String.format("%-23s%-5s%6d%30s%-5d%s%s",time, log.getBusiness().getLevel(), log.getPid(), log.getMethodName(), log.getLineNumber(), log.getClassName(), log.getBusiness().getContent());
////            %-23s%-5s%6d%30s%-5d %s %s
//            print.flush();
//            print.write(logMsg.toString().getBytes());
//            print.write("\r\n".getBytes());
//            System.out.println(String.format("2017-04-24 17:37:30.030  INFO %-5s", "13400"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


}
