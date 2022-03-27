package com.github.vo;

/**
 * @author coffe enginner
 * @date 2022/3/23 21:16
 * @description 执行计划结果实体
 */
public class ExplainResultVO {

    /**
     * 类型
     */
    private String type;

    /**
     * 扫描行数
     */
    private Long rows;

    /**
     * 其他项
     */
    private String extra;

    public String getType() {
        return type;
    }

    public ExplainResultVO setType(String type) {
        this.type = type;
        return this;
    }

    public Long getRows() {
        return rows;
    }

    public ExplainResultVO setRows(Long rows) {
        this.rows = rows;
        return this;
    }

    public String getExtra() {
        return extra;
    }

    public ExplainResultVO setExtra(String extra) {
        this.extra = extra;
        return this;
    }

    @Override
    public String toString() {
        return "ExplainResultVO{" +
                "type='" + type + '\'' +
                ", rows=" + rows +
                ", extra='" + extra + '\'' +
                '}';
    }
}
