# auto-explain
自动分析执行计划插件
# 注意事项
1. 需要添加以下配置
    1.  spring:
          mail:
            host: smtp.qq.com #使用QQ邮箱发送邮箱
            port: 465         #QQ邮箱端口
            username:         #使用QQ邮箱发送得账号
            receiver:         #邮件接收者邮箱账号
            subject:          #邮件发送主题
            password:         #QQ邮箱授权码,登录QQ邮箱->设置->账号->开启服务->开启:POP3/SMTP服务,获取到授权码
            properties:
              mail:
                smtp:
                  socketFactory:
                    class: javax.net.ssl.SSLSocketFactory #必须使用SSL发送
    2.  mybatis:
          enable:
            auto:
              explain: true             #是否开启执行计划自动执行
            mail:
              push-explain-warn: true   #是否开启内置告警功能,特别注意:若开启该功能,必须配置邮箱配置(即i所述配置),以自动注入JavaMailSenderImpl,若未开启该功能,可以自定义Spring监听器,监听对象为com.github.event.PushWarnMsgEvent,示例:com.github.listener.PushWarnMsgByMailListener
2.
