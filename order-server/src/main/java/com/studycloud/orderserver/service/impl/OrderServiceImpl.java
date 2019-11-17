package com.studycloud.orderserver.service.impl;

import com.studycloud.client.ProductClient;
import com.studycloud.common.DecreaseStockInput;
import com.studycloud.common.ProductInfoOutput;
import com.studycloud.orderserver.dataObject.OrderDetail;
import com.studycloud.orderserver.dataObject.OrderMaster;
import com.studycloud.orderserver.dto.OrderDTO;
import com.studycloud.orderserver.enums.OrderStatusEnum;
import com.studycloud.orderserver.enums.PayStatusEnum;
import com.studycloud.orderserver.enums.ResultEnum;
import com.studycloud.orderserver.exception.OrderException;
import com.studycloud.orderserver.mapper.OrderDetailMapper;
import com.studycloud.orderserver.mapper.OrderMasterMapper;
import com.studycloud.orderserver.service.OrderService;
import com.studycloud.orderserver.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    OrderMasterMapper orderMasterMapper;
    @Autowired
    ProductClient productClient;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        //生成orderid
        String orderid = KeyUtil.genUniqueKey();
        //TODO 查询商品信息
        /**
         * 改造为秒杀系统
         *   1.读redis
         *   2.减库存并将值重新设置进redis
         *   3.1 加redis锁 订单入库失败 redis手动回滚
         *   3.2 加redis锁 订单入库成功，发送消息
         *
         *
         */
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfoOutput> productInfos = productClient.listForOrder(productIdList);
        //TODO 计算总价
        BigDecimal orderAmount = new BigDecimal(0);
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            for(ProductInfoOutput productInfo:productInfos){
                if(orderDetail.getProductId().equals(productInfo.getProductId())){
                    orderAmount =  productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderid);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    orderDetail.setProductPrice(productInfo.getProductPrice().doubleValue());
                    //订单详情入库
                    orderDetailMapper.saveOrderDetail(orderDetail);
                }
            }
        }
        //TODO 扣库存
        List<DecreaseStockInput> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(cartDTOList);
        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderid);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterMapper.saveOrderMaster(orderMaster);

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        //1. 查询订单
        OrderMaster orderMaster = orderMasterMapper.findById(orderId);
        if(orderMaster == null){
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2. 判断订单状态
        if(orderMaster.getOrderStatus() != OrderStatusEnum.NEW.getCode()){
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3. 修改订单状态为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        orderMasterMapper.updateOrderStatus(orderMaster.getOrderId());
        //查询订单详情
        List<OrderDetail> orderDetails = orderDetailMapper.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetails)){
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }
}
