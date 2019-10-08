package com.example.tacocloud.controller;

import com.example.tacocloud.model.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping ("/orders")
public class OrderController {
    @GetMapping("/current")
    public String orderForm(Model model){
        //这里会接收到taco对象，应该放到order对象中
        model.addAttribute("order", new Order());
        return "orderForm";
    }
    @PostMapping
    public String processOrder(@Valid Order order, Errors errors){
        if(errors.hasErrors()){
            return "orderForm";
        }
        log.info("Order submitted: " +order);
        return "redirect:/";
    }
}
