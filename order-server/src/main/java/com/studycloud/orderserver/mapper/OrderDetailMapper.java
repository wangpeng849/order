package com.studycloud.orderserver.mapper;

import com.studycloud.orderserver.dataObject.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderDetailMapper {

    @Insert(
            "insert into order_detail " +
            "(detail_id,order_id,product_id,product_name,product_price,product_quantity) " +
            "values (#{orderDetail.detailId},#{orderDetail.orderId},#{orderDetail.productId},#{orderDetail.productName},#{orderDetail.productPrice},#{orderDetail.productQuantity})"
    )
    void saveOrderDetail(@Param("orderDetail") OrderDetail orderDetail);
    
}
