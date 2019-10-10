package com.example.tacocloud.dao;

import com.example.tacocloud.dao.Interface.OrderRepository;
import com.example.tacocloud.model.Order;
import com.example.tacocloud.model.Taco;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");//用数据库自动生成的id
        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");
        this.objectMapper = new ObjectMapper();
    }


    private long saveOrderDetails(Order order) {
        @SuppressWarnings("unchecked")
                Map<String,Object> values= objectMapper.convertValue(order,Map.class);//to convert an Order into a Map
        values.put("placeAt",order.getPlacedAt());//再加一条属性placeAt进去，让convert转的话，date会转成long,而PlacedAT是是date
        long orderId= orderInserter.executeAndReturnKey(values).longValue();//This saves the order information to the Taco_Order table and returns,the database-generated ID as a Number object
        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId){
        Map<String,Object> values=new HashMap<>();
        values.put("tacoOrder",orderId);
        values.put("taco",taco.getId());
        orderTacoInserter.execute(values);// A simple call to the orderTacoInserter ’s execute() method performs the insert.
    }
    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        Long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for (Taco taco : tacos) {
            saveTacoToOrder(taco, orderId);
        }
        return  order;
    }


}
