package com.github.vo;

/**
 * @author coffe enginner
 * @date 2022/3/26 18:10
 * @description 推送告警信息实体
 */
public class PushWarnMsgVO {

    /**
     * 告警信息
     */
    private String warnMsg;

    public String getWarnMsg() {
        return warnMsg;
    }

    public PushWarnMsgVO setWarnMsg(String warnMsg) {
        this.warnMsg = warnMsg;
        return this;
    }
}
