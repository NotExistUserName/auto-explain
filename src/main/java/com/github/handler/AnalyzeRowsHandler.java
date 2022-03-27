package com.github.handler;

import com.github.vo.AnalyzeExplainResultVO;
import com.github.vo.ExplainResultVO;

import java.util.List;

/**
 * @author coffe enginner
 * @date 2022/3/24 9:41
 * @description 分析执行计划扫描行数处理器
 */
public class AnalyzeRowsHandler extends AbstractAnalyzeExplainResultBaseHandler{

    /**
     * 扫描行数阈值
     */
    private Long scanRowsThreshold;

    public AnalyzeRowsHandler(Long scanRowsThreshold, AbstractAnalyzeExplainResultBaseHandler nextHandler) {
        if (scanRowsThreshold == null || scanRowsThreshold <= 0) {
            throw new IllegalArgumentException("scanRowsThreshold cannot be null or less then zero.");
        }
        this.scanRowsThreshold = scanRowsThreshold;
        this.setNextHandler(nextHandler);
    }

    @Override
    public void doAnalyze(List<ExplainResultVO> explainResultVoList, AnalyzeExplainResultVO analyzeExplainResultVo) {
        LOGGER.info("AnalyzeRowsHandler start to analyze explanation of rows");
        long scanRows = 1L;
        for (int i = 0; i < explainResultVoList.size(); i++) {
            ExplainResultVO explainResultVo = explainResultVoList.get(i);
            Long rows = explainResultVo.getRows();
            if (rows == null) {
                analyzeExplainResultVo.buildNeedPushWarnMsg("扫描行数：为空,请立即检查");
                return;
            }
            scanRows *= rows;
        }
        if (scanRows >= scanRowsThreshold) {
            analyzeExplainResultVo.buildNeedPushWarnMsg(String.format("扫描行数：%s，超出阈值：%s，请立即检查",scanRows,scanRowsThreshold));
        }
    }
}
