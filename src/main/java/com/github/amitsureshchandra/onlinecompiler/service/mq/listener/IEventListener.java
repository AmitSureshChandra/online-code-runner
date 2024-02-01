package com.github.amitsureshchandra.onlinecompiler.service.mq.listener;

import org.springframework.messaging.handler.annotation.Payload;

public interface IEventListener {
    void listenCodeEvent(String dto);
}
