package com.studycloud.orderserver.controller;

import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@DefaultProperties(defaultFallback = "defaultFallback")
@RestController
public class HystrixController {

    //    @HystrixCommand(fallbackMethod="fallback")
    @GetMapping("/getProductInfoList")
//    @HystrixCommand(commandProperties={
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "3000")//超时时间改3s
//    })    --->写入配置文件
    @HystrixCommand
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
//            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
//            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
//            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
//    })
    public String getProductInfoList(@RequestParam("number") Integer number) {
        if(number%2==0){
            return "success";
        }
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:8081/product/listForOrder",
                Arrays.asList("1001"),
                String.class);
    }

    private String fallback() {
        return "太拥挤了，稍后再试。";
    }

    private String defaultFallback() { //加上@Hystix默认进入此方法
        return "默认提示： 太拥挤了，请稍后再试。";
    }
}
