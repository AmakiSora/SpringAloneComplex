package com.cosmos.feign;

import com.cosmos.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Feign调用接口
 * @FeignClient(name = "",path = "",configuration =)
 * name指定调用rest接口所对应的服务名
 * path指定调用rest接口所在的@RequestMapping("")
 * configuration指定使用的Feign配置
 */
@FeignClient(name = "stock-service",path = "/stock",configuration = FeignConfig.class)
public interface StockFeignService {
    //声明需要调用的rest接口对应的方法
    @GetMapping("/reduce")
    String reduce();
}
//以下是对应源码
//@RestController
//@RequestMapping("/stock")
//public class StockController {
//    @GetMapping("/reduce")
//    public String reduce(){
//        System.out.println("减少库存");
//        return "减少库存";
//    }
//}
