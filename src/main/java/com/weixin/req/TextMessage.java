package com.weixin.req;

/**
 * @author lw
 * @version 1.0
 * @describe 文本消息
 */
public class TextMessage extends BaseMessage {
    //消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
