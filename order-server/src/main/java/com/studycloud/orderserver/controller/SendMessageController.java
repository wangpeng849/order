package com.studycloud.orderserver.controller;


import com.studycloud.orderserver.dto.OrderDTO;
import com.studycloud.orderserver.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.zip.DataFormatException;

@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

    @GetMapping("/send")
    public void process(){
        streamClient.output().send(MessageBuilder.withPayload("now :"+ new Date()).build());
    }

    /**
     * 发送OrderDTO对象
     */
    @GetMapping("/sendMessage")
    public void processObject(){
        OrderDTO orderDTO  = new OrderDTO();
        streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
    }
}
