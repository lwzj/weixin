package com.weixin.resp;

/**
 * @author lw
 * @version 1.0
 * @describe 推送视频消息
 */
public class VideoMessage extends BaseMessage {
    // 视频
    private Video Video;

    public com.weixin.resp.Video getVideo() {
        return Video;
    }

    public void setVideo(com.weixin.resp.Video video) {
        Video = video;
    }
}
