## 安装Android应用程序 ##
	 adb install apk在PC上的路径.apk
## 删除Android应用程序 ##
	adb uninstall apk的包名 (可在手机的data/data下查看包名)
## push 从电脑上传送文件到手机 ##
	adb push 电脑源文件路径 手机目标文件路径
## pull 从手机传送文件到电脑上 ##
	adb pull 手机源文件路径 电脑目标文件路径
## 同一局域网内用无线安装 ##
[传送门](http://blog.csdn.net/alexbxp/article/details/7242083)  

	adb connect ip:port -> adb install 路径.apk
## 查看连接的手机 ##
	adb devices
## 重启adb ##
1. 输入adb kill-server并按下Enter键
2. 输入adb start-server并按下Enter键。
## error: more than one device and emulator  ##
**解决方法**  

- 可能是连接的多个Android设备
- 如果确定只连接了一台设备，则可以使用
	adb kill-server
- 在进程里结束ADB.exe 
## 录制视频 ##
默认录制180秒，按下ctrl+c可以提前结束录制  

    adb shell screenrecord /sdcard/test.mp4
### 设定视频分辨率 ###
对于高分辨率的手机，录制的视频很大，我们分享又不需要这么大的  
我们可以设置录制的视频分辨率  

	adb shell screenrecord --size 848*480 /sdcard/test.mp4
### 设定视频比特率 ###
默认比特率是4M/s，为了分享方便，我们可以调低比特率为2M  

	adb shell screenrecord --bit-rate 2000000 /sdcard/test.mp4
### 获取视频文件 ###
使用adb pull 即可把手机SD卡中视频获取到电脑D盘  

	adb pull /sdcard/test.mp4 d:/
