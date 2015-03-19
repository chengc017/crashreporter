# crashreporter

crash reporter for android


###useage: 

CrashReporter.initialize().usingCollectorAddress("mobile-collector.mmtrix.com/mobile_crash").start(getApplicationContext());

###crashdata structure:
` ``javascript
{\<br>
    "protocolVersion": 1,\<br> 
    "platform": "Android",\<br> 
    "uuid": "设备唯一标识码",\<br> 
    "buildId": "",\<br> 
    "timestamp": "1970.1.1开始计算的秒数",
    "appToken": "licence key",
    "deviceInfo":{
                     "memoryUsage": "内存使用数",
                     "orientation": "屏幕排列",    // ORIENTATION_UNDEFINED = 0, ORIENTATION_PORTRAIT = 1, ORIENTATION_LANDSCAPE = 2
                     "networkStatus": "网络运营商",
                     "diskAvailable": [            //"磁盘使用情况"
                                          "root目录磁盘空闲字节数",
                                          "扩展卡空闲字节数"
                                      ],
                     "osVersion": "系统release版本号",
                     "deviceName": "设备名字",
                     "osBuild": "设备build版本号",
                     "architecture": "设备os架构版本",
                     "runTime": "jvm版本",
                     "modelNumber": "The end-user-visible name for the end product.",
                     "screenResolution": "屏幕分辨率字符串",
                     "deviceUuid": "设备uuid"
                 },
    "appInfo": {
                   "appName": "app名字",
                   "appVersion": "app版本",
                   "bundleId": "app的packageId",
                   "processId": "app进程id"
               },
    "exceptionInfo": {
                         "name": "类名",
                         "cause": "异常message"
                     },
    "threads": {
                   "crashed": "true/false",
                   "state": "线程状态信息",
                   "threadNumber": "线程ID",
                   "threadId": "线程名",
                   "priority": "线程优先级",
                   "stack":{
                               "fileName": "文件名",
                               "className": "类名",
                               "methodName": "方法名",
                               "lineNume": "异常行号"
                           } 
               },
    "activityHistory": [        //crash前的activity调用历史
                           [
                               "activity name",
                               "activity开始时间(MS)",
                               "activity持续时间(MS)"
                           ],
                           ...    
                       ],
    "dataToken": [0, 0]
}
` ``
