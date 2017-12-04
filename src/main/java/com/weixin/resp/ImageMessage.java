package com.weixin.resp;

/**
 * @author lw
 * @version 1.0
 * @describe 推送图片消息
 */
public class ImageMessage extends BaseMessage {
    private Image Image;

    public com.weixin.resp.Image getImage() {
        return Image;
    }

    public void setImage(com.weixin.resp.Image image) {
        Image = image;
    }
}
