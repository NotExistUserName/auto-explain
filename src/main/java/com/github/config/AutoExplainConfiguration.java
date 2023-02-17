package com.github.config;

import com.github.handler.AbstractAnalyzeExplainResultBaseHandler;
import com.github.interceptor.AutoExplainInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Description: 执行计划自动执行配置类
 * @author: xiexing01
 * @date: 2023/2/15
 */
@Configuration
@ConditionalOnProperty(prefix = "mybatis.enable.auto",name = "explain",havingValue = "true")
//导入咱们需要的配置类，注入邮件发送监听者与springboot邮件发送对象
@Import(value = AutoInjectBeansConfiguration.class)
public class AutoExplainConfiguration {

    public static final Logger LOGGER = LoggerFactory.getLogger(AutoExplainConfiguration.class);

    /**
     * 注入mybatis执行计划拦截器
     *
     * @return
     */
    @Bean
    public AutoExplainInterceptor autoExplainInterceptor() {
        LOGGER.info("AutoExplainInterceptor has bean injected..");
        return new AutoExplainInterceptor();
    }
}
