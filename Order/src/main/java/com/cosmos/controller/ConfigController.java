package com.cosmos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope//刷新配置文件,不加@Value不会变
public class ConfigController {
    //配置中心
    @Value("${test.msg}")//获取配置信息,貌似可以用@NacosValue
            String msg;
    @GetMapping("/getConfig")//查询config
    public String getConfig(){
        return msg;
    }
}
