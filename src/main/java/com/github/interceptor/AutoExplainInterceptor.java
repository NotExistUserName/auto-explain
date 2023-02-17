package com.github.interceptor;

import com.github.config.AutoInjectBeansConfiguration;
import com.github.constant.SqlCommandConstant;
import com.github.event.PushWarnMsgEvent;
import com.github.handler.AnalyzeExtraHandler;
import com.github.handler.AnalyzeRowsHandler;
import com.github.handler.AnalyzeTypeHandler;
import com.github.utils.SqlHandleUtils;
import com.github.vo.AnalyzeExplainResultVO;
import com.github.vo.ExplainResultVO;
import com.github.vo.PushWarnMsgVO;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author coffe enginner
 * @date 2022/3/22 20:55
 * @description 自动执行SQL执行计划拦截器
 */

/**
 * 咱们拦截StatementHandler处理器的prepare，方便咱们获取Connection（数据库连接）以及ParameterHandler（参数设置对象）
 */
@Intercepts(
        @Signature(type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class})
)
public class AutoExplainInterceptor implements Interceptor {

    public static final Logger LOGGER = LoggerFactory.getLogger(AutoExplainInterceptor.class);

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 需要推送告警信息的类型关键字，多个以英文逗号分割
     */
    @Value("${mybatis.need.warn-msg-type:ALL}")
    private String needPushWarnMsgTypeKeyWords;

    /**
     * 扫描行数告警阈值,默认为10000
     */
    @Value("${mybatis.scan.rows-threshold:10000}")
    private Long scanRowsThreshold;

    /**
     * 需要推送告警信息的其他项关键字，多个以英文逗号分割
     */
    @Value("${mybatis.need.warn-msg-extra:Using filesort}")
    private String needPushWarnMsgExtraKeyWords;

    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        Object[] args = invocation.getArgs();
        if (target instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) target;
            BoundSql boundSql = statementHandler.getBoundSql();
            //原始sql
            String originSql = boundSql.getSql();
            if (!StringUtils.startsWithIgnoreCase(originSql, SqlCommandConstant.PREFIX_SELECT)) {
                //非查询语句，直接返回
                return invocation.proceed();
            }
            //生成执行计划sql
            String explainSql = SqlHandleUtils.genExplainSql(originSql);
            //获取数据连接
            Connection connection = (Connection) args[0];
            //预编译执行计划sql
            PreparedStatement preparedStatement = connection.prepareStatement(explainSql);
            ParameterHandler parameterHandler = statementHandler.getParameterHandler();
            //对sql设置参数
            parameterHandler.setParameters(preparedStatement);
            //执行执行计划
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ExplainResultVO> explainResultVoList = SqlHandleUtils.buildExplainResult(resultSet);
            if (CollectionUtils.isEmpty(explainResultVoList)) {
                throw new IllegalArgumentException("execute explain error.");
            }
            //创建责任链分析执行计划结果
            AnalyzeExplainResultVO analyzeExplainResultVo = new AnalyzeExplainResultVO();
            analyzeExplainResultVo.setNeedPushWarnMsg(false);
            AnalyzeTypeHandler analyzeExplainHandlerChain = new AnalyzeTypeHandler(needPushWarnMsgTypeKeyWords,
                                                            new AnalyzeRowsHandler(scanRowsThreshold,
                                                            new AnalyzeExtraHandler(needPushWarnMsgExtraKeyWords,null)));
            analyzeExplainHandlerChain.analyze(explainResultVoList,analyzeExplainResultVo);
            //是否需要异步推送告警推送告警
            if (analyzeExplainResultVo.getNeedPushWarnMsg()) {
                String warnMsg = SqlHandleUtils.buildWarnMsg(SqlHandleUtils.formatSql(originSql),analyzeExplainResultVo.getErrMsg());
                LOGGER.error(warnMsg);
                this.publishWarnMsgEvent(warnMsg);
            }
        }
        return invocation.proceed();
    }

    /**
     * 发布事件异步推送告警
     *
     * @param warnMsg
     */
    private void publishWarnMsgEvent(String warnMsg) {
        applicationContext.publishEvent(new PushWarnMsgEvent(new PushWarnMsgVO().setWarnMsg(warnMsg)));
    }
}
