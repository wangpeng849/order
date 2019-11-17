package com.studycloud.orderserver.mapper;

import com.studycloud.orderserver.dataObject.OrderMaster;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderMasterMapper {
    @Insert(
            "insert into order_master " +
                    "(order_id,buyer_name,buyer_phone,buyer_address,buyer_openid,order_amount,order_status,pay_status) " +
                    "values (#{orderMaster.orderId},#{orderMaster.buyerName},#{orderMaster.buyerPhone},#{orderMaster.buyerAddress},#{orderMaster.buyerOpenid}," +
                    "#{orderMaster.orderAmount},#{orderMaster.orderStatus},#{orderMaster.payStatus})"
    )
    void saveOrderMaster(@Param("orderMaster") OrderMaster orderMaster);

    @Select("select order_id as orderId," +
            "buyer_name as buyerName," +
            "buyer_phone as buyerPhone," +
            "buyer_address as buyerAddress," +
            "buyer_openid as buyerOpenid," +
            "order_amount as orderAmount," +
            "order_status as orderStatus," +
            "pay_status as payStatus " +
            "from order_master where order_id = #{orderId}")
    OrderMaster findById(String orderId);

    @Update("update order_master set order_status = 1 where order_id = #{orderId}")
    void updateOrderStatus(String orderId);
}

