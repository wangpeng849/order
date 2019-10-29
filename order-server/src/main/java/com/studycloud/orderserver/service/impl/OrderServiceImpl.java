package com.studycloud.orderserver.service.impl;

import com.studycloud.client.ProductClient;
import com.studycloud.common.DecreaseStockInput;
import com.studycloud.common.ProductInfoOutput;
import com.studycloud.orderserver.dataObject.OrderDetail;
import com.studycloud.orderserver.dataObject.OrderMaster;
import com.studycloud.orderserver.dataObject.ProductInfo;
import com.studycloud.orderserver.dto.CartDTO;
import com.studycloud.orderserver.dto.OrderDTO;
import com.studycloud.orderserver.enums.OrderStatusEnum;
import com.studycloud.orderserver.enums.PayStatusEnum;
import com.studycloud.orderserver.mapper.OrderDetailMapper;
import com.studycloud.orderserver.mapper.OrderMasterMapper;
import com.studycloud.orderserver.service.OrderService;
import com.studycloud.orderserver.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public OrderDTO create(OrderDTO orderDTO) {
        //生成orderid
        String orderid = KeyUtil.genUniqueKey();
        //TODO 查询商品信息
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
                            .multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderid);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
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
}
