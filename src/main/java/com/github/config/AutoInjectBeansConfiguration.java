package com.github.config;

import com.github.listener.PushWarnMsgByMailListener;
import com.github.utils.SendMailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author coffe enginner
 * @date 2022/3/24 21:33
 * @description 自动注入bean对象
 */
@Configuration
public class AutoInjectBeansConfiguration {

    public static final Logger LOGGER = LoggerFactory.getLogger(AutoInjectBeansConfiguration.class);

    @Bean
    @ConditionalOnProperty(prefix = "mybatis.enable.mail",name = "push-explain-warn",havingValue = "true")
    @DependsOn(value = "sendMailUtils")
    public PushWarnMsgByMailListener pushWarnMsgByMailListener() {
        LOGGER.info("PushWarnMsgByMailListener has bean injected..");
        return new PushWarnMsgByMailListener();
    }

    @Bean
    @ConditionalOnProperty(prefix = "mybatis.enable.mail",name = "push-explain-warn",havingValue = "true")
    @DependsOn(value = "mailSender")
    public SendMailUtils sendMailUtils() {
        LOGGER.info("SendMailUtils has bean injected..");
        return new SendMailUtils();
    }

}
