package com.github.listener;

import com.github.event.PushWarnMsgEvent;
import com.github.utils.SendMailUtils;
import com.github.vo.PushWarnMsgVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

/**
 * @author coffe enginner
 * @date 2022/3/26 18:13
 * @description 通过邮件推送告警信息监听者
 */
public class PushWarnMsgByMailListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(PushWarnMsgByMailListener.class);

    @Autowired
    private SendMailUtils sendMailUtils;

    @EventListener
    public void eventListener(PushWarnMsgEvent pushWarnMsgEvent) {
        LOGGER.info("listened push warn msg by mail..");
        PushWarnMsgVO pushWarnMsgVo = (PushWarnMsgVO) pushWarnMsgEvent.getSource();
        sendMailUtils.sendMail(pushWarnMsgVo.getWarnMsg());
    }
}
