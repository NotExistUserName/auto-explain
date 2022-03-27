package com.github.listener;

import com.github.event.PushWarnMsgEvent;
import com.github.utils.SendMailUtils;
import com.github.vo.PushWarnMsgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

/**
 * @author coffe enginner
 * @date 2022/3/26 18:13
 * @description 通过邮件推送告警信息监听者
 */
public class PushWarnMsgByMailListener {

    @Autowired
    private SendMailUtils sendMailUtils;

    @EventListener
    public void eventListener(PushWarnMsgEvent pushWarnMsgEvent) {
        PushWarnMsgVO pushWarnMsgVo = (PushWarnMsgVO) pushWarnMsgEvent.getSource();
        sendMailUtils.sendMail(pushWarnMsgVo.getWarnMsg());
    }
}
