package com.github.handler;

import com.github.vo.AnalyzeExplainResultVO;
import com.github.vo.ExplainResultVO;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author coffe enginner
 * @date 2022/3/23 22:53
 * @description 分析执行计划类型处理器
 */
public class AnalyzeTypeHandler extends AbstractAnalyzeExplainResultBaseHandler{

    /**
     * 需要推送告警信息的类型关键字，多个以英文逗号分割
     */
    private String needPushWarnMsgTypeKeyWords;

    public AnalyzeTypeHandler(String needPushWarnMsgTypeKeyWords,AbstractAnalyzeExplainResultBaseHandler nextHandler) {
        if (StringUtils.isEmpty(needPushWarnMsgTypeKeyWords)) {
            throw new IllegalArgumentException("needPushWarnMsgTypeKeyWords cannot be null.");
        }
        this.needPushWarnMsgTypeKeyWords = needPushWarnMsgTypeKeyWords;
        this.setNextHandler(nextHandler);
    }

    @Override
    public void doAnalyze(List<ExplainResultVO> explainResultVoList, AnalyzeExplainResultVO analyzeExplainResultVo) {
        LOGGER.info("AnalyzeTypeHandler start to analyze explanation of type");
        for (int i = 0; i < explainResultVoList.size(); i++) {
            ExplainResultVO explainResultVo = explainResultVoList.get(i);
            if (StringUtils.isEmpty(explainResultVo.getType())) {
                analyzeExplainResultVo.buildNeedPushWarnMsg("扫描类型：为空,请立即检查");
                return;
            }
            if (needPushWarnMsgTypeKeyWords.contains(explainResultVo.getType())) {
                analyzeExplainResultVo.buildNeedPushWarnMsg(String.format("扫描类型：%s,请立即检查",explainResultVo.getType()));
                return;
            }
        }
    }
}
