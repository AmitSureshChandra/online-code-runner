package com.github.amitsureshchandra.onlinecoderunner.service.mq.processor;

public interface IEventProcessor<T> {
    void process(T dto);
}
