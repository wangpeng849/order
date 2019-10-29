package com.studycloud.orderserver.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.studycloud.orderserver.dataObject.OrderDetail;
import com.studycloud.orderserver.dto.OrderDTO;
import com.studycloud.orderserver.enums.ResultEnum;
import com.studycloud.orderserver.exception.OrderException;
import com.studycloud.orderserver.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList  = new ArrayList<>();

        Gson gson = new Gson();
        try{
            orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("【json转换】错误，string={}",orderForm.getItems());
            throw new OrderException(ResultEnum.PARRAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
