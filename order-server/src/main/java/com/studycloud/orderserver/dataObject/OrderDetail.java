package com.studycloud.orderserver.dataObject;


import lombok.Data;

@Data
public class OrderDetail {
    private String detailId;
    private String orderId;
    private String productId;
    private String productName;
    private double productPrice;
    private Integer productQuantity;
}
