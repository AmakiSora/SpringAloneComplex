package com.cosmos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {
    @GetMapping("/reduce")
    public String reduce(){
        System.out.println("减少库存");
        return "减少库存";
    }
}
