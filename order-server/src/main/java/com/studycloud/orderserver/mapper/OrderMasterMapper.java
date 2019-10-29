package com.studycloud.orderserver.mapper;

import com.studycloud.orderserver.dataObject.OrderMaster;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
}
