package com.example.tacocloud.controller.Discount;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "taco.discount")//从application.yaml中读取相应前缀的数值
public class DiscountCodeProps {
    private Map<String,Integer> codes=new HashMap<>();
}
