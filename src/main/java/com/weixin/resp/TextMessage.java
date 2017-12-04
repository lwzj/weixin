package com.weixin.resp;

/**
 * @author lw
 * @version 1.0
 * @describe 推送文字消息
 */
public class TextMessage extends BaseMessage {
    // 回复的消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
