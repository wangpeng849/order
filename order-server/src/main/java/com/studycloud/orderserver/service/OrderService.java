package com.studycloud.orderserver.service;


import com.studycloud.orderserver.dto.OrderDTO;

public interface OrderService {
    OrderDTO create(OrderDTO orderDTO);
}
