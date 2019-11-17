package com.studycloud.orderserver.mapper;

import com.studycloud.orderserver.dataObject.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderDetailMapper {

    @Insert(
            "insert into order_detail " +
            "(detail_id,order_id,product_id,product_name,product_price,product_quantity) " +
            "values (#{orderDetail.detailId},#{orderDetail.orderId},#{orderDetail.productId},#{orderDetail.productName},#{orderDetail.productPrice},#{orderDetail.productQuantity})"
    )
    void saveOrderDetail(@Param("orderDetail") OrderDetail orderDetail);

    @Select("select detail_id as detailId," +
            "order_id as orderId," +
            "product_id as productId," +
            "product_name as productName," +
            "product_price as productPrice," +
            "product_quantity as productQuantity" +
            " from order_detail where order_id = #{orderId}")
    List<OrderDetail> findByOrderId(String orderId);
}
