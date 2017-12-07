package com.weixin.resp;

/**
 * @author lw
 * @version 1.0
 * @describe 推送音乐消息
 */
public class MusicMessage extends BaseMessage {
    // 音乐
    private com.weixin.resp.Music Music;

    public com.weixin.resp.Music getMusic() {
        return Music;
    }

    public void setMusic(com.weixin.resp.Music music) {
        Music = music;
    }
}
