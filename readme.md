#   日志记录工具
V<b>1.0.0</b>

新功能:预新增MCC(method call chain)功能

V<b>0.0.4</b>

毫无修改,只想好好发个版本

V<b>0.0.3_1</b>

1.修复因class名字太长.在缩短时,取得是第二字母而不是第一个字母

2.修复logger.info()方法重载的bug.

#版本变更<br>
v<b>0.0.3</b><br>
1.删除Logfactory中脑残的代码

2.增加<b><span style="color:red">@ContentType</span></b>,<b><span style="color:red">@Channel</span></b>用来分别指定日志内容的内容,以及实时日志队列的Topic.

3.去掉色彩日志输出.

V<b>0.0.21</b>

1.提供日志级别配置

V<b>0.0.2</b>

1.提供kafka日志对接.(使用<b>Kafka Appender</b>时,请加上<b>FileAppender</b>做好<b>Kafka</b>连接断开时的日志备份)

V<b>0.0.1</b>

1.提供<b>Console</b>日志输出

2.提供<b>File</b>日志输出


#使用说明
#### 在项目资源文件夹添加log.yml文件
```+yaml
logLevel:   Debug //日志级别
targets:    #记录器
  - File    #启用文件Appender
  - Sout    #启用控制台Appender
  - Kafka   #启用Kafka Appender
kafka:
  kafkaServer:  192.168.2.56:8084   #日志服务器地址
  kafkaKey: test                    #kafka key(向日志服务器申请)
  kafkaCert:  test                  #kafka Cert(向日志服务器申请)
  kafkaBackDir: D:/test/kafka/      #Kafka链接失败备份文件路径
host: 0.0.0.0   # 项目启动占用IP
port: 2222      # 项目启动占用端口
application:  "springboot application"  #项目名称
fileProperties:         #如果设置了Targets-File一定要配置该项
  fileDir:  D:/test/    #日志输出目录
  fileName: log.log     #日志输出文件
  rolling:  Hour        #日志切割按照间隔时间
  fileSize: 1MB         #日志切割按照安件大小
#日志输出模板 
#   %t  时间
#   %l  日志等级
#   %p  进程号
#   %mn 方法名
#   %ln 行号
#   %cn 类名
#   %c  日志内容
partten:  "%-23{yyyy-MM-dd HH:mm:ss.sss}t %5l %-5p --- [%15mn:%ln] %-41cn: %c"    

```

#### pom.xml添加
```+xml
<dependency>
  <groupId>com.daqsoft.log</groupId>
  <artifactId>log-util</artifactId>
  <version>0.0.21</version>
</dependency>
```
#### 使用方式
```+java
    public class LoggerDemo {
        Logger logger = LogFactory.getLogger(LoggerDemo.class);
        @Test
        //这里添加LogModel注解标识 当前方法是属于什么模块.Eg:用户模块
        @LogModel("测试demo")
        //这里用来指定实时日志队列topic,放在方法上,那么这个方法所有的日志类型都会被输出到这个topic上
        @Channel("Jikebao")
        public void log() throws InterruptedException {
            logger.info("测试info");
            logger.info("测试%s","info");
            logger.debug("测试debug");
            logger.debug("测试%s","debug");
            logger.error("测试error");
            //如果需要单条输出到日志队列中,在info中指定.无法指定其他日志级别单条输出.
            logger.info("Jikebao","测试日志队列");
            logger.error("测试%s","error");
            logger.warn("测试warn");
            logger.warn("测试%s","warn");
            logger.log("测试自定级别", Constans.ERROR);
        }
    }
```

---
                        Spilt Line
---

### 时序图

![Log时序图](doc/img/sequence.png "Log时序图")
  
上图为最基本的时序图,其中个别过程并没有画出.异步操作放在了最后的Appender处理

