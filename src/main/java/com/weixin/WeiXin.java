package com.weixin;

import com.weixin.baidu.aip.imageclassify.AipImageClassify;
import com.weixin.pojo.Button;
import com.weixin.pojo.ClickButton;
import com.weixin.pojo.Menu;
import com.weixin.pojo.ViewButton;
import com.weixin.utils.AuthUtil;
import com.weixin.utils.HttpClientUtil;
import com.weixin.utils.MessageUtil;
import com.weixin.utils.SignUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class WeiXin {
    private Logger logger = LoggerFactory.getLogger(WeiXin.class);
    //设置APPID/AK/SK
    public static final String APP_ID = "10467479";
    public static final String API_KEY = "FBnzAdj8q6ainVGE5jHbcvzC";
    public static final String SECRET_KEY = "bh6mHQv3GIddC9WqoSC3Lzhe6x3ekGtZ";

    /**
     * 验证确实是微信发来的请求
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value="WXServlet",method= RequestMethod.GET)
    public void WXServlet2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        if(SignUtil.checkSignature(signature, timestamp, nonce)){
            logger.debug("wolaile"+echostr);
            out.print(echostr);
        }
    }

    /**
     * 响应发出的请求
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value="WXServlet",method= RequestMethod.POST)
    public void WXServlet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Map<String, String> map = MessageUtil.parseXml(request);
            String ToUserName = map.get("ToUserName");
            String FromUserName = map.get("FromUserName");
//			String CreateTime = map.get("CreateTime");
            String MsgType = map.get("MsgType");
            String Content = map.get("Content");
//			String MsgId = map.get("MsgId");

            String message = "";
            if (MessageUtil.REQ_MESSAGE_TYPE_IMAGE.equals(MsgType)) {
                String MediaId = map.get("MediaId");
                String token = AuthUtil.getInstance().get_access_token().get("access_token");
                String url = AuthUtil.GET_FILE_URL.replace("ACCESS_TOKEN", token);
                url+="&media_id="+MediaId;
//                message = MessageUtil.initText(ToUserName, FromUserName, token);
//                 初始化一个AipImageCensor
                AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

                // 可选：设置网络连接参数
                client.setConnectionTimeoutInMillis(2000);
                client.setSocketTimeoutInMillis(60000);
                //先判断这是不是一辆车
                org.json.JSONObject res = client.carDetect(url,new HashMap<String, String>());
                HashMap hashMap = (HashMap) res.toMap();
                ArrayList<HashMap> result = (ArrayList<HashMap>) hashMap.get("result");
                double i = 0;
                for (HashMap value: result) {
                    if(Double.parseDouble(value.get("score").toString())>i) {
                        i = Double.parseDouble(value.get("score").toString());
                        message = value.get("name").toString();
                        message = MessageUtil.initText(ToUserName, FromUserName, message);
                    }
                }
                if (i == 1) {//非汽车，检查是不是动物
                    org.json.JSONObject res2 = client.animalDetect(url, new HashMap<String, String>());
                    hashMap = (HashMap) res.toMap();
                    result = (ArrayList<HashMap>) hashMap.get("result");
                    i = 0;
                    for (HashMap value: result) {
                        if(Double.parseDouble(value.get("score").toString())>i) {
                            i = Double.parseDouble(value.get("score").toString());
                            message = value.get("name").toString();
                            message = MessageUtil.initText(ToUserName, FromUserName, message);
                        }
                    }
                }
                if (i == 1) {//非动物，检查是不是植物
                    org.json.JSONObject res2 = client.plantDetect(url, new HashMap<String, String>());
                    hashMap = (HashMap) res2.toMap();
                    result = (ArrayList<HashMap>) hashMap.get("result");
                    i = 0;
                    for (HashMap value: result) {
                        if(Double.parseDouble(value.get("score").toString())>i) {
                            i = Double.parseDouble(value.get("score").toString());
                            message = value.get("name").toString();
                            message = MessageUtil.initText(ToUserName, FromUserName, message);
                        }
                    }
                }
                if (i == 1) {//非植物，检查是不是动物
                    org.json.JSONObject res2 = client.plantDetect(url, new HashMap<String, String>());
                    hashMap = (HashMap) res2.toMap();
                    result = (ArrayList<HashMap>) hashMap.get("result");
                    i = 0;
                    for (HashMap value: result) {
                        if(Double.parseDouble(value.get("score").toString())>i) {
                            i = Double.parseDouble(value.get("score").toString());
                            message = value.get("name").toString();
                            message = MessageUtil.initText(ToUserName, FromUserName, message);
                        }
                    }
                }

            }else {
                message = MessageUtil.initTulLing(ToUserName, FromUserName, Content);
            }

            System.out.println(message);
            out.print(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getFile(String MediaId) {
        String token = AuthUtil.getInstance().get_access_token().get("access_token");
        String url = AuthUtil.GET_FILE_URL.replace("ACCESS_TOKEN", token);
        url+="&media_id="+MediaId;
        JSONObject jsonObject = HttpClientUtil.doGetStr(url);
        return jsonObject;
    }


    /**
     * 自定义菜单创建
     * @return
     */
    @RequestMapping("createMenu")
    @ResponseBody
    public JSONObject createMenu(HttpServletRequest request, HttpServletResponse response){
//		String menu = inputToString(request);
        String menu = JSONObject.fromObject(initMenu()).toString();
        String token = AuthUtil.getInstance().get_access_token().get("access_token");
        String url = AuthUtil.CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = HttpClientUtil.doPostStr(url,menu);
        return jsonObject;
    }

    /**
     * 组装菜单
     * @return
     */
    public static Menu initMenu(){
        Menu menu = new Menu();
        ClickButton button11 = new ClickButton();
        button11.setName("ClickButton");
        button11.setType("click");
        button11.setKey("11");

        ViewButton button21 = new ViewButton();
        button21.setName("ViewButton");
        button21.setType("view");
        button21.setUrl("http://magicabc.com.cn");

        ClickButton button31 = new ClickButton();
        button31.setName("扫码");
        button31.setType("scancode_push");
        button31.setKey("31");

        ClickButton button32 = new ClickButton();
        button32.setName("地理位置");
        button32.setType("location_select");
        button32.setKey("32");

        ClickButton button33 = new ClickButton();
        button33.setName("联系我们");
        button33.setType("click");
        button33.setKey("33");

        ClickButton button34 = new ClickButton();
        button34.setName("客服帮助");
        button34.setType("click");
        button34.setKey("34");

        ClickButton button35 = new ClickButton();
        button35.setName("我是老师");
        button35.setType("click");
        button35.setKey("35");

        Button button = new Button();
        button.setName("关于我们");
        button.setSub_button(new Button[]{button31,button32,button33,button34,button35});

        menu.setButton(new Button[]{button11,button21,button});
        return menu;
    }
}
