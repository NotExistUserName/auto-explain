package com.github.config;

import com.github.listener.PushWarnMsgByMailListener;
import com.github.utils.SendMailUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author coffe enginner
 * @date 2022/3/24 21:33
 * @description 自动注入bean对象
 */
@Configuration
public class AutoInjectBeansConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "mybatis.enable.mail",name = "push-explain-warn",havingValue = "true")
    public PushWarnMsgByMailListener pushWarnMsgByMailListener() {

        return new PushWarnMsgByMailListener();
    }

    @Bean
    @ConditionalOnProperty(prefix = "mybatis.enable.mail",name = "push-explain-warn",havingValue = "true")
    public SendMailUtils sendMailUtils() {
        return new SendMailUtils();
    }

}
