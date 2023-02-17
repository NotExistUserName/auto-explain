package com.github.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.concurrent.CompletableFuture;

/**
 * @author coffe enginner
 * @date 2022/3/25 9:04
 * @description 邮件发送工具类
 */
public class SendMailUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(SendMailUtils.class);

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送端的用户邮箱名
     */
    @Value("${spring.mail.username}")
    private String username;

    /**
     * 邮件接收人,多个以英文逗号分割
     */
    @Value("${spring.mail.receiver}")
    private String receiver;

    /**
     * 邮件发送主题
     */
    @Value("${spring.mail.subject}")
    private String subject;

    /**
     * 发送纯文本邮件.
     *
     * @param text 纯文本内容
     */
    public void sendMail(String text) {
        LOGGER.info("starting send email,text:{}", text);
        this.sendMail(receiver, subject, text);
    }

    /**
     * 发送纯文本邮件.
     *
     * @param to      目标email 地址
     * @param subject 邮件主题
     * @param text    纯文本内容
     */
    public void sendMail(String to, String subject, String text) {
        CompletableFuture.runAsync(() -> {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(username);
                message.setTo(to.split(","));
                message.setSubject(subject);
                message.setText(text);
                javaMailSender.send(message);
                LOGGER.info("告警邮件发送成功");
            } catch (Exception e) {
                LOGGER.error("push warn msg mail fail，error msg：{}", e.getMessage(), e);
            }
        });
    }
}
