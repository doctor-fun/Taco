package com.example.tacocloud.controller;

import com.example.tacocloud.dao.Interface.OrderRepository;
import com.example.tacocloud.model.Order;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping ("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepo;
    public OrderController(OrderRepository orderRepo){
        this.orderRepo=orderRepo;
    }



    @GetMapping("/current")
         //这里会接收到taco对象，应该放到order对象中
    public String orderForm(){
        //model.addAttribute("order", new Order());
        return "orderForm";
    }
    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus){
        if(errors.hasErrors()){
            return "orderForm";
        }
        orderRepo.save(order);
        sessionStatus.setComplete();//因为controller要求在session中保留order，如果不setcomplete，老的order会一直在session中，setComplete会是的session重置
        log.info("Order submitted: " +order);
        return "redirect:/";
    }
}
