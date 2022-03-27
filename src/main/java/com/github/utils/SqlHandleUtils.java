package com.github.utils;

import com.github.constant.ExplainColNameConstant;
import com.github.constant.SqlCommandConstant;
import com.github.vo.ExplainResultVO;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author coffe enginner
 * @date 2022/3/23 20:46
 * @description SQL 处理工具
 */
public final class SqlHandleUtils {

    private SqlHandleUtils(){}

    /**
     * 生成执行计划sql
     *
     * @param originSql
     * @return
     */
    public static String genExplainSql(String originSql) {
        if (StringUtils.isEmpty(originSql)) {
            throw new NullPointerException("originSql cannot be empty.");
        }
        return SqlCommandConstant.PREFIX_EXPLAIN + originSql;
    }

    /**
     * 构建explain执行结果
     *
     * @param resultSet
     * @return
     */
    public static List<ExplainResultVO> buildExplainResult(ResultSet resultSet) throws Exception{
        if (resultSet == null) {
            throw new NullPointerException("ResultSet cannot be null.");
        }
        List<ExplainResultVO> explainResultVoList = new ArrayList<ExplainResultVO>();
        while (resultSet.next()) {
            ExplainResultVO explainResultVo = new ExplainResultVO();
            //类型
            String type = resultSet.getString(ExplainColNameConstant.COL_NAME_TYPE);
            //扫描行数
            long rows = resultSet.getLong(ExplainColNameConstant.COL_NAME_ROWS);
            //其他项
            String extra = resultSet.getString(ExplainColNameConstant.COL_NAME_EXTRA);
            explainResultVo.setType(type).setRows(rows).setExtra(extra);
            explainResultVoList.add(explainResultVo);
        }
        return explainResultVoList;
    }

    /**
     * 构建告警信息
     *
     * @param originSql
     * @param errMsg
     * @return
     */
    public static String buildWarnMsg(String originSql,String errMsg){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("原始SQL:").append(originSql).append("\r\n")
                .append("错误信息:").append(errMsg);
        return stringBuilder.toString();
    }

    /**
     * 将sql中的一些空格去除
     *
     * @param originSql
     * @return
     */
    public static String formatSql(String originSql) {
        if (StringUtils.isEmpty(originSql)) {
            return originSql;
        }
        return originSql.replaceAll("\\s+"," ");
    }
}
