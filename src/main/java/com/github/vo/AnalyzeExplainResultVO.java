package com.github.vo;

/**
 * @author coffe enginner
 * @date 2022/3/23 22:50
 * @description 分析执行计划结果实体
 */
public class AnalyzeExplainResultVO {

    /**
     * 是否需要推送告警信息
     */
    private Boolean needPushWarnMsg;

    /**
     * 告警信息
     */
    private String errMsg;

    public void buildNeedPushWarnMsg(String errMsg) {
        this.needPushWarnMsg = true;
        this.errMsg = errMsg;
    }

    public Boolean getNeedPushWarnMsg() {
        return needPushWarnMsg;
    }

    public AnalyzeExplainResultVO setNeedPushWarnMsg(Boolean needPushWarnMsg) {
        this.needPushWarnMsg = needPushWarnMsg;
        return this;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public AnalyzeExplainResultVO setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
