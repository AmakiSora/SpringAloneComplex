package com.cosmos.controller;

import com.cosmos.feign.StockFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    StockFeignService stockFeignService;
    @GetMapping("/add")//使用restTemplate来进行远程调用
    public String add(){
        System.out.println("下单成功");
        String msg = restTemplate.getForObject("http://stock-service/stock/reduce", String.class);
        System.out.println(msg);
        return "下单成功"+msg;
    }
    @GetMapping("/add2")//使用OpenFeign来进行远程调用
    public String add2(){
        System.out.println("下单成功");
        String msg = stockFeignService.reduce();
        System.out.println(msg);
        return "下单成功"+msg;
    }
}
