package com.github.amitsureshchandra.onlinecoderunner.service.mq.listener;

public interface IEventListener {
    void listenCodeEvent(String dto);
}
