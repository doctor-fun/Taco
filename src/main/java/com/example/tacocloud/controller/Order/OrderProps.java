package com.example.tacocloud.controller.Order;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Component//It’s also annotated with @Component so that Spring component scanning will automati-
//cally discover it and create it as a bean in the Spring application context.
@ConfigurationProperties(prefix="taco.orders")//从application.yml找到前缀为这个的属性
@Data
public class OrderProps {
    @Min(value=5,message="must be between 5 and 25")
    @Max(value=25,message="must be between 5 and 25")
    private int pageSize=20;

}
