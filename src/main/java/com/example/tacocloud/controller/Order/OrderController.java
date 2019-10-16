package com.example.tacocloud.controller.Order;

import com.example.tacocloud.dao.Interface.OrderRepository;
import com.example.tacocloud.model.Order;
import com.example.tacocloud.security.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;


@Slf4j
@Controller
@RequestMapping ("/orders")
@SessionAttributes("order")
//@ConfigurationProperties(prefix="taco.orders")//when setting the pageSize property, you need to use a configuration property named
//taco.orders.pageSize
//在applicaiton.yml中定义每页实际显示数据
public class OrderController {

    private OrderRepository orderRepo;
    private OrderProps props;

    public OrderController(OrderRepository orderRepo,OrderProps props) {
        this.orderRepo = orderRepo;
        this.props=props;
    }


    @GetMapping("/current")
    //这里会接收到taco对象，应该放到order对象中
    public String orderForm(@AuthenticationPrincipal User user, @ModelAttribute Order order) {
        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullname());
        }//order的某个值没有的话，就从注册用户信息里拿，填补
        if (order.getDeliveryStreet() == null) {
            order.setDeliveryStreet(user.getStreet());
        }
        if (order.getDeliveryCity() == null) {
            order.setDeliveryCity(user.getCity());
        }
        if (order.getDeliveryState() == null) {
            order.setDeliveryState(user.getState());
        }
        if (order.getDeliveryZip() == null) {
            order.setDeliveryZip(user.getZip());
        }
        //model.addAttribute("order", new Order());
        return "orderForm";
    }

    @PostMapping
    //authenticationPrincipal是个已授权用户信息类
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        //以下两句是登录用户的信息放进来
        // User user=(User) authentication.getPrincipal();//principal是返回用户信息对象的;授权模块保存了登录用户的信息
        order.setUser(user);


        orderRepo.save(order);
        sessionStatus.setComplete();//因为controller要求在session中保留order，如果不setcomplete，老的order会一直在session中，setComplete会是的session重置
        log.info("Order submitted: " + order);
        return "redirect:/";
    }
//    @GetMapping
//    public String orderForUser(@AuthenticationPrincipal User user, Model model){
//        model.addAttribute("orders",orderRepo.findByUserOrderByPlacedAtDesc(user));
//        return "orderList";
//    }
    //带分页
    //硬编码pagesize
//    private  int pageSize=20;
//    public void setPageSize(int pageSize){
//        this.pageSize=pageSize;
//}

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model){
        Pageable pageable= PageRequest.of(0,props.getPageSize());//设置每页的显示第0页接受20个结果
        model.addAttribute("orders",orderRepo.findByUserOrderByPlacedAtDesc(user,pageable));
        return "orderList";
    }
}
