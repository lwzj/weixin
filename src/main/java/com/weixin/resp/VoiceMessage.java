package com.weixin.resp;

/**
 * @author lw
 * @version 1.0
 * @describe 推送语音消息
 */
public class VoiceMessage extends BaseMessage {
    // 语音
    private Voice Voice;

    public com.weixin.resp.Voice getVoice() {
        return Voice;
    }

    public void setVoice(com.weixin.resp.Voice voice) {
        Voice = voice;
    }
}
