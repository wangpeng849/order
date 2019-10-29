//package com.studycloud.orderserver.controller;
//
//import com.studycloud.client.ProductClient;
//import com.studycloud.order.dataObject.ProductInfo;
//import com.studycloud.order.dto.CartDTO;
//import com.studycloud.orderserver.dataObject.ProductInfo;
//import com.studycloud.orderserver.dto.CartDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@RestController
//public class ClientController {
//
//    @Autowired
//    LoadBalancerClient loadBalancerClient;
//
//    @Autowired
//    RestTemplate restTemplate;
//
//    @RequestMapping("/getMsg")
//    public String getMessage() {
//        //第一种方式(直接使用restTemplate)
////        RestTemplate restTemplate = new RestTemplate();
////        String response = restTemplate.getForObject("http://localhost:8081/msg", String.class);
//
////       第二种方式（先或许host和port在使用restTemplate）
////        RestTemplate restTemplate = new RestTemplate();
////        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
////        serviceInstance.getHost();
////        serviceInstance.getPort();
////        String url = String.format("http://%s:%s/msg", serviceInstance.getHost(), serviceInstance.getPort());
////        String response = restTemplate.getForObject(url, String.class);
//
//
//        //第三种方式
//        String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);
//        return response;
//    }
//
//    @Autowired
//    ProductClient productClient;
//
//    @GetMapping("/getProductMsg")
//    public String getProductMsg() {
//        String resp = productClient.productMsg();
//        return resp;
//    }
//
//    @PostMapping("/getProductList")
//    public String getProductList(){
//        List<ProductInfo> productInfos = productClient.listForOrder(Arrays.asList("123123123"));
//        return "ok";
//    }
//
//    @PostMapping("/productDesStock")
//    public String desStock( ){
//        List cartDTOList = new ArrayList();
//        cartDTOList.addAll(Arrays.asList(new CartDTO("123123123",2)));
//        productClient.desStock(cartDTOList);
//        return "ok";
//    }
//}
