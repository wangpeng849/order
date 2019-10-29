package com.studycloud.orderserver.controller;

import com.studycloud.orderserver.dataObject.OrderDetail;
import com.studycloud.orderserver.dataObject.OrderMaster;
import com.studycloud.orderserver.enums.OrderStatusEnum;
import com.studycloud.orderserver.enums.PayStatusEnum;
import com.studycloud.orderserver.mapper.OrderDetailMapper;
import com.studycloud.orderserver.mapper.OrderMasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class OrderTestController {


    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    OrderMasterMapper orderMasterMapper;

    @RequestMapping("/orderDetail")
    public String orderDetail(OrderDetail orderDetail){
        orderDetail.setDetailId("detailId");
        orderDetail.setOrderId("123123");
        orderDetail.setProductId("123123123");
        orderDetail.setProductName("hotdog");
        orderDetail.setProductPrice(200);
        orderDetail.setProductQuantity(2);
        orderDetailMapper.saveOrderDetail(orderDetail);
        return "success";
    }

    @RequestMapping("/orderMaster")
    public String orderMaster(OrderMaster orderMaster){
        orderMaster.setOrderId("123123");
        orderMaster.setBuyerName("wang");
        orderMaster.setBuyerPhone("182xxxxyyyy");
        orderMaster.setBuyerAddress("where are you");
        orderMaster.setBuyerOpenid("no open id");
        orderMaster.setOrderAmount(new BigDecimal(5000000));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterMapper.saveOrderMaster(orderMaster);
        return "success";
    }
}
