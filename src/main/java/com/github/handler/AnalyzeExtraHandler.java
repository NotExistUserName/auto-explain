package com.github.handler;

import com.github.vo.AnalyzeExplainResultVO;
import com.github.vo.ExplainResultVO;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author coffe enginner
 * @date 2022/3/24 9:46
 * @description 分析执行计划其他项处理器
 */
public class AnalyzeExtraHandler extends AbstractAnalyzeExplainResultBaseHandler{

    /**
     * 需要推送告警信息的其他项关键字，多个以英文逗号分割
     */
    private String needPushWarnMsgExtraKeyWords;

    public AnalyzeExtraHandler(String needPushWarnMsgExtraKeyWords,AbstractAnalyzeExplainResultBaseHandler nextHandler) {
        if (StringUtils.isEmpty(needPushWarnMsgExtraKeyWords)) {
            throw new IllegalArgumentException("needPushWarnMsgExtraKeyWords cannot be null");
        }
        this.setNextHandler(nextHandler);
    }

    @Override
    public void doAnalyze(List<ExplainResultVO> explainResultVoList, AnalyzeExplainResultVO analyzeExplainResultVo) {
        LOGGER.info("AnalyzeExtraHandler start to analyze explanation of extra");
        for (int i = 0; i < explainResultVoList.size(); i++) {
            ExplainResultVO explainResultVo = explainResultVoList.get(i);
            if (needPushWarnMsgExtraKeyWords.contains(explainResultVo.getExtra())) {
                analyzeExplainResultVo.buildNeedPushWarnMsg(String.format("其他项包含：%s，请立即检查",explainResultVo.getExtra()));
                return;
            }
        }
    }
}
