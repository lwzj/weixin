package com.weixin.baidu.aip;

import com.weixin.baidu.aip.imageclassify.AipImageClassify;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "10467479";
    public static final String API_KEY = "FBnzAdj8q6ainVGE5jHbcvzC";
    public static final String SECRET_KEY = "bh6mHQv3GIddC9WqoSC3Lzhe6x3ekGtZ";
    public static void main(String[] args) throws IOException {
        // 初始化一个AipImageCensor
        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

//         可选：设置代理服务器地址, http和socket二选一，或者均不设置
        client.setHttpProxy("127.0.0.1", 8080);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 调用接口
        String path = "F:\\io\\ceh.jpg";
        JSONObject res = client.carDetect(path,new HashMap<String, String>());
        System.out.println(res.toString(2));

    }
}
