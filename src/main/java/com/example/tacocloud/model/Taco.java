package com.example.tacocloud.model;

import lombok.Data;
import lombok.Generated;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {

    @Id //jpa要用
    @GeneratedValue(strategy = GenerationType.AUTO)//表示由数据库生成
    private  Long id;
    private Date createdAt;

    // end::allButValidation[]
    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    // tag::allButValidation[]
    private String name;

    private Date createAt;


    @ManyToMany(targetEntity = Ingredient.class)//一个ingredients可以包含很多ingredient,一个ingredient也可以对应多个Taco
    @Size(min=1, message="You must choose at least 1 ingredient")
    // tag::allButValidation[]
    private List<Ingredient> ingredients;

    @PrePersist//表示在持久化的时机执行以下函数
    void createAt(){
        this.createAt= new Date();
    }

}