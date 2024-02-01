package com.github.amitsureshchandra.onlinecompiler.service.mq.processor;

import com.github.amitsureshchandra.onlinecompiler.dto.event.CodeEventDto;

public interface IEventProcessor<T> {
    void process(T dto);
}
