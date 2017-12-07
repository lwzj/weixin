package com.weixin.pojo;
/**
 * view、miniprogram类型必须
 * 网页链接，用户点击菜单可打开链接，不超过1024字节。
 * type为miniprogram时，不支持小程序的老版本客户端将打开本url。
 * @author lj
 *
 */
public class ViewButton extends Button{

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
