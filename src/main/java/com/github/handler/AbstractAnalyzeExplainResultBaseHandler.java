package com.github.handler;

import com.github.utils.JsonUtils;
import com.github.vo.AnalyzeExplainResultVO;
import com.github.vo.ExplainResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author coffe enginner
 * @date 2022/3/23 22:43
 * @description 分析执行计划结果抽象基类
 */
public abstract class AbstractAnalyzeExplainResultBaseHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(AbstractAnalyzeExplainResultBaseHandler.class);

    /**
     * 下一处理器
     */
    private AbstractAnalyzeExplainResultBaseHandler nextHandler;

    /**
     * 分析执行计划结果
     *
     * @param explainResultVoList
     * @return
     */
    public void analyze(List<ExplainResultVO> explainResultVoList,AnalyzeExplainResultVO analyzeExplainResultVo) {
        LOGGER.info(String.format("explainResultVoList：%s", JsonUtils.object2StringQuietly(explainResultVoList)));
        doAnalyze(explainResultVoList,analyzeExplainResultVo);
        if (analyzeExplainResultVo.getNeedPushWarnMsg()) {
            return;
        }
        if (nextHandler != null) {
            nextHandler.analyze(explainResultVoList,analyzeExplainResultVo);
        }
    }

    /**
     * 执行真正的分析
     *
     * @param explainResultVoList
     * @param analyzeExplainResultVO
     */
    public abstract void doAnalyze(List<ExplainResultVO> explainResultVoList,AnalyzeExplainResultVO analyzeExplainResultVO);

    public AbstractAnalyzeExplainResultBaseHandler getNextHandler() {
        return nextHandler;
    }

    public AbstractAnalyzeExplainResultBaseHandler setNextHandler(AbstractAnalyzeExplainResultBaseHandler nextHandler) {
        this.nextHandler = nextHandler;
        return this;
    }
}
