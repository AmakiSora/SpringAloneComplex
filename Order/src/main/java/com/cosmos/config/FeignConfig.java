package com.cosmos.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign的配置
 * 全局配置: 当使用@Configuration,会将配置作用于所有的服务提供方
 * 局部配置: 如果只是针对某一服务进行配置,就不要加@Configuration,可以通过配置类(方法1)或者配置文件(方法2)
 */
//@Configuration
public class FeignConfig {
    //日志(方法1)
    @Bean
    public Logger.Level feignLoggerLevel(){
        /*
        日志级别有四种:
            NONE:不记录任何日志(默认)[性能最好,适用于生产环境]
            BASIC:仅记录请求方法,URL,响应状态代码以及执行时间[适用于生产环境追踪问题]
            HEADERS:记录BASIC级别的基础上,记录请求和响应的header
            FULL:记录请求和响应的header,body和元数据[适用于开发环境以及测试环境定位问题]
         */
        return Logger.Level.FULL;
    }
}
