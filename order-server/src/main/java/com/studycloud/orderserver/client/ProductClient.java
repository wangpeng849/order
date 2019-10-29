package com.studycloud.orderserver.client;


import com.studycloud.orderserver.dataObject.ProductInfo;
import com.studycloud.orderserver.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//@FeignClient(name="product")
//public interface ProductClient {
//
//    @GetMapping("/msg")
//    String productMsg();
//
//    @PostMapping(value="/product/listForOrder")
//    List<ProductInfo> listForOrder(@RequestBody List<String> productIdList);
//
//    @PostMapping("/product/desStock")
//     String desStock(@RequestBody List<CartDTO> cartDTOList);
//
//}
