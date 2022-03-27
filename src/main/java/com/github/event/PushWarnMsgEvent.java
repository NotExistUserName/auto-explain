package com.github.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author coffe enginner
 * @date 2022/3/26 18:06
 * @description 推送告警信息事件
 */
public class PushWarnMsgEvent extends ApplicationEvent {

    public PushWarnMsgEvent(Object source) {
        super(source);
    }
}
