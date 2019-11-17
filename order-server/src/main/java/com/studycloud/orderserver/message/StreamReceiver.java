package com.studycloud.orderserver.message;

import com.studycloud.orderserver.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

    //    @StreamListener(StreamClient.INPUT_VALUE)
    //    public void process(Object message){
    //        log.info("StreamReciver:"+message);
    //    }


    @StreamListener(StreamClient.INPUT_VALUE)
    @SendTo(StreamClient.INPUT_VALUE2)
    public String processObject(OrderDTO message){
        log.info("StreamReciver:"+message);
        return "receiver to repone:";
    }

    @StreamListener(StreamClient.INPUT_VALUE2)
    public void processObjectResponse(String message){
        log.info("StreamReciver2:"+message);
    }


}
