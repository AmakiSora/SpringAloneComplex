package com.cosmos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.cosmos.feign.StockFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
@RefreshScope//刷新配置文件,不加@Value不会变
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
    //配置中心
    @Value("${test.msg}")//获取配置信息,貌似可以用@NacosValue
    String msg;
    @GetMapping("/getConfig")//查询config
    public String getConfig(){
        return msg;
    }
}
