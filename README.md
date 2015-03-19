# crashreporter

crash reporter for android


###useage: 

CrashReporter.initialize().usingCollectorAddress("mobile-collector.mmtrix.com/mobile_crash").start(getApplicationContext());

###crashdata structure:

`''Bash<br>
{<br>
    "protocolVersion": 1,<br> 
    "platform": "Android",<br> 
    "uuid": "设备唯一标识码",<br> 
    "buildId": "",<br> 
    "timestamp": "1970.1.1开始计算的秒数",<br>
    "appToken": "licence key",<br>
    "deviceInfo":{<br>
                     "memoryUsage": "内存使用数",<br>
                     "orientation": "屏幕排列",    // ORIENTATION_UNDEFINED = 0, ORIENTATION_PORTRAIT = 1,<br> ORIENTATION_LANDSCAPE = 2<br>
                     "networkStatus": "网络运营商",<br>
                     "diskAvailable": [            //"磁盘使用情况"<br>
                                          "root目录磁盘空闲字节数",<br>
                                          "扩展卡空闲字节数"<br>
                                      ],<br>
                     "osVersion": "系统release版本号",<br>
                     "deviceName": "设备名字",<br>
                     "osBuild": "设备build版本号",<br>
                     "architecture": "设备os架构版本",<br>
                     "runTime": "jvm版本",<br>
                     "modelNumber": "The end-user-visible name for the end product.",<br>
                     "screenResolution": "屏幕分辨率字符串",<br>
                     "deviceUuid": "设备uuid"<br>
                 },<br>
    "appInfo": {<br>
                   "appName": "app名字",<br>
                   "appVersion": "app版本",<br>
                   "bundleId": "app的packageId",<br>
                   "processId": "app进程id"<br>
               },<br>
    "exceptionInfo": {<br>
                         "name": "类名",<br>
                         "cause": "异常message"<br>
                     },<br>
    "threads": {<br>
                   "crashed": "true/false",<br>
                   "state": "线程状态信息",<br>
                   "threadNumber": "线程ID",<br>
                   "threadId": "线程名",<br>
                   "priority": "线程优先级",<br>
                   "stack":{<br>
                               "fileName": "文件名",<br>
                               "className": "类名",<br>
                               "methodName": "方法名",<br>
                               "lineNume": "异常行号"<br>
                           } <br>
               },<br>
    "activityHistory": [        //crash前的activity调用历史<br>
                           [<br>
                               "activity name",<br>
                               "activity开始时间(MS)",<br>
                               "activity持续时间(MS)"<br>
                           ],<br>
                           ...    <br>
                       ],<br>
    "dataToken": [0, 0]<br>
}<br>
`''
