package com.studycloud.orderserver;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 发送mq消息
 */
@Component
public class MqSenderTest extends OrderServerApplicationTests{
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void send(){
        amqpTemplate.convertAndSend("myQueue","now time:"+new Date());
    }

    @Test
    public void sendOrder(){
        amqpTemplate.convertAndSend("myOrder","computer","now time:"+new Date());
    }
}
