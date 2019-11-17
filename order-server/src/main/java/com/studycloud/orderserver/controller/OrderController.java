package com.studycloud.orderserver.controller;

import com.studycloud.orderserver.VO.ResultVo;
import com.studycloud.orderserver.convert.OrderForm2OrderDTOConverter;
import com.studycloud.orderserver.dto.OrderDTO;
import com.studycloud.orderserver.enums.ResultEnum;
import com.studycloud.orderserver.exception.OrderException;
import com.studycloud.orderserver.form.OrderForm;
import com.studycloud.orderserver.service.OrderService;
import com.studycloud.orderserver.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {


    @Autowired
    OrderService orderService;

    @RequestMapping("/create")
    public ResultVo<Map<String,String>> orderDetail(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm={}",orderForm);
            throw new OrderException(ResultEnum.PARRAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        // orderForm ---> orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);

        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车信息为空");
            throw new OrderException(ResultEnum.CART_ERROR);
        }

        OrderDTO result = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());
        return ResultUtil.success(map);
    }

    @PostMapping("/finish")
    public ResultVo<OrderDTO> finish(@RequestParam("orderId")String orderId){
        return ResultUtil.success(orderService.finish(orderId));
    }
}
