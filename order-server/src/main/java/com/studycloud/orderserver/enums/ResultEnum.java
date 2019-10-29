package com.studycloud.orderserver.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PARRAM_ERROR(1,"参数错误"),
    CART_ERROR(2,"购物车为空"),
        SUCCESS(0,"成功");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
