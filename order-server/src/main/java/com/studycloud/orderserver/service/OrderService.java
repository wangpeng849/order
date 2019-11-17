package com.studycloud.orderserver.service;


import com.studycloud.orderserver.dto.OrderDTO;

public interface OrderService {
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    OrderDTO finish(String orderId);
}
