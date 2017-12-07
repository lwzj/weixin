package com.weixin.utils;

import com.weixin.pojo.Token;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthUtil {

    public static final String APPID = "wxa6899ae7e6ee40e0";
    public static final String APPSECRET = "54843172c987bf6377c14f8c0ecd907b";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
    private static final String CALLBACK_IP_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
    public static final String UPLOADIMG_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
    public static final String CARD_CREATE = "https://api.weixin.qq.com/card/create?access_token=ACCESS_TOKEN";
    public static final String QRCODE_CREATE = "https://api.weixin.qq.com/card/qrcode/create?access_token=TOKEN";
    public static final String SHOW_QRCODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
    public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    public static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    public static final String GET_FILE_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN";

    private AuthUtil(){};
    private static AuthUtil instance = null;
    public static AuthUtil getInstance(){
        if(instance==null){
            instance = new AuthUtil();
        }
        return instance;
    }

    private Map<String, String> map = new HashMap<String, String>();

    /**
     * 获取token
     * @return
     */
    private static Token getAccessToken(){
        Token token = new Token();
        JSONObject jsonObject = HttpClientUtil.doGetStr(ACCESS_TOKEN_URL);
        if(jsonObject!=null){
            System.out.println(jsonObject.toString());
            token.setAccessToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return token;
    }
    //获取微信服务器IP地址
    private static String getcallbackip(String token){
        String result = "";
        String url = CALLBACK_IP_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = HttpClientUtil.doGetStr(url);
        if(jsonObject!=null){
            result = jsonObject.getString("ip_list");
        }
        return result;
    }

    /**
     * 跟新token
     * @return
     */
    public Map<String, String> get_access_token(){
        String time = map.get("time");//更新token的时间
        String accessToken = map.get("access_token");
        Long nowDate = new Date().getTime();//现在的时间
        if (accessToken != null && time != null && nowDate - Long.parseLong(time) < 7000 * 1000) {
            System.out.println("accessToken存在，且没有超时 ， 返回单例");
        } else {
            System.out.println("accessToken 超时 ， 或者不存在 ， 重新获取");
            Token access_token=getAccessToken();
            map.put("time", nowDate + "");
            map.put("access_token", access_token.getAccessToken());
        }

        return map;
    }
    public static void main(String[] args) {
        String token = AuthUtil.getInstance().get_access_token().get("access_token");
//        getcallbackip(token);
        System.out.println(token);
    }
}
