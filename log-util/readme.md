   日志记录工具
----
使用说明
在项目资源文件夹添加log.yml文件
```+yaml
targets:
  - File
  - Sout
host: 0.0.0.0
port: 2222
application:  "springboot application"
fileProperties:
  fileDir:  D:/test/
  fileName: log.log
  rolling:  Hour
  fileSize: 1MB
partten:  "%-23{yyyy-MM-dd HH:mm:ss.sss}t %5l %-5p --- [%15mn:%ln] %-41cn: %c"
```