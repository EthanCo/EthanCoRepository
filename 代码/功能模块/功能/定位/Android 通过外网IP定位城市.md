# Android 通过外网IP定位城市 #
有两种方法  
方法一:通过获取外网IP地址，再通过聚合数据的IP定位API 进行城市定位  
方法二:通过百度地图API进行定位 (无需添加SDK)

## 方法一 ##

### 获取外网IP ###

	/**
     * 通过CmyIP获取获取外网外网地址  需在异步线程中访问
     * @return 外网IP
     */
    public static String getOuterNetFormCmyIP() {
        String response = NetWorkUtil.GetOuterNetIp("http://www.cmyip.com/");
        Pattern pattern = Pattern
                .compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
        Matcher matcher = pattern.matcher(response.toString());
        if (matcher.find()) {
            String group = matcher.group();
            return group;
        }

        return null;
    }

	/**
     * 获取获取外网外网地址  需在异步线程中访问
     * @param ipaddr 提供外网服务的服务器ip地址
     * @return       外网IP
     */
    public static String GetOuterNetIp(String ipaddr) {
        URL infoUrl = null;
        InputStream inStream = null;
        try {
            infoUrl = new URL(ipaddr);
            URLConnection connection = infoUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
                StringBuilder strber = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                    strber.append(line + "\n");
                inStream.close();
                return strber.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

#### 添加权限 ####

	<uses-permission android:name="android.permission.INTERNET" />

#### 使用 ####

	new Thread() {
        @Override
        public void run() {
            String outerIp = NetWorkUtil.getOuterNetFormCmyIP();
            Log.i(TAG, "outerIp: " + outerIp);
        }
    }.start();

### 根据外网IP定位城市 ###

可通过[聚合数据的API](https://www.juhe.cn/docs/api/id/1)实现  

## 方法二 ##

通过百度地图开放平台提供的 [普通IP定位API](http://lbsyun.baidu.com/index.php?title=webapi/ip-api) (无需添加SDK)，从而实现