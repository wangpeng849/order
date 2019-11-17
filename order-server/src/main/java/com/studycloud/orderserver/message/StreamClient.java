package com.studycloud.orderserver.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface StreamClient {
    public final static String INPUT_VALUE = "myMessage";
    public final static String INPUT_VALUE2 = "myMessageResponse";

    @Input(StreamClient.INPUT_VALUE)
    SubscribableChannel input();

    @Output(StreamClient.INPUT_VALUE)
    MessageChannel output();

    @Input(StreamClient.INPUT_VALUE2)
    SubscribableChannel inputResponse();

    @Output(StreamClient.INPUT_VALUE2)
    MessageChannel outputResponse();
}
