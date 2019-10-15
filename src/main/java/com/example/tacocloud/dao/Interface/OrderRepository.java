package com.example.tacocloud.dao.Interface;

import com.example.tacocloud.model.Order;
import com.example.tacocloud.security.model.User;

import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {

//    List<Order> findByDeliveryZip(String deliveryZip); //zip邮政编码,spring data尝试根据方法名理解语义（DSL）
//    //因为CrudRepo...<order,long>给的参数是order，所以find方法找order ，by列名Deliveryzip 值为deliveryzip
//    //Repository methods are composed of a verb, an optional subject, the word By, and a predicate. In the case of findByDeliveryZip() ,
//    // the verb is find and the predicate is DeliveryZip; the subject isn’t specified and is implied to be an Order
//
//
//    List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);
//    //count,read,find,get动词
//    @Query("Order o where o.deliveryCity='Seattle'")
//    List<Order> readOrdersDeliveredInSeattle();
    List<Order>  findByUserOrderByPlacedAtDesc(User user);

}
