package com.cosmos.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign自定义拦截器
 */
public class CustomFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {//拦截后做的事(授权,日志等)
        requestTemplate.header("xxx","xxx");
        System.out.println("Feign拦截器");
    }
}
