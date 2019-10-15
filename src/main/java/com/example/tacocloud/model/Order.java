package com.example.tacocloud.model;

import com.example.tacocloud.security.model.User;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;


import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data//生成有参数，setter,getter
@Entity//jpa要用
@Table(name="Taco_Order")//This specifies that Order entities should
//be persisted to a table named Taco_Order in the database.
public class Order implements Serializable {

    private static final  long serialVersionUID= 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private Date placedAt;
    @NotBlank(message = "Name is required")
    private  String deliveryName;;
    @NotBlank(message = "Street is required")
    private String deliveryStreet;
    @NotBlank(message="City is required")
    private  String deliveryCity;
    @NotBlank(message="State is required")
    private String deliveryState;
    @NotBlank(message="Zip code is required")
    private String deliveryZip;
    @CreditCardNumber(message="Not a valid credit card number")//This
//    annotation declares that the property’s value must be a valid credit card number that
//    passes the Luhn algorithm check
    private String ccNumber;

    //日期0*/**为主
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",message="Must be formatted MM/YY")
    private String ccExpiration;

    //*用户与订单做绑定*每个用户可以用多个订单
    @ManyToOne
    private User user;


    //3位有效数字，没有小数点？
    @Digits(integer = 3,fraction =0,message = "Invalid CVV")
    private String ccCVV;

    @ManyToMany(targetEntity = Taco.class)//一个
    private List<Taco> tacos=new ArrayList<>();

    @PrePersist //在持久化的时机生成这个插入的时间点
    void placedAt(){
        this.placedAt= new Date();
    }





    public void addDesign(Taco design){
        this.tacos.add(design);
    }
}
