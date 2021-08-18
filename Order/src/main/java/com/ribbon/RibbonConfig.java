package com.ribbon;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ribbon负载均衡配置类(方法1)
 * 一般配置在服务发起端
 * 要加上@RibbonClients注解,具体在OrderApplication里有
 * 注意:不要放在springboot能扫描到的地方,如果被springboot扫描到,不然就会作用于全局，启动就会报错
 */
@Configuration
public class RibbonConfig {//默认为轮询
    @Bean
    public IRule iRule(){
        return new RandomRule();//随机法
    }
}
