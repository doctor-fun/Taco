package com.example.tacocloud.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data//会生成一个带参数构造器
@RequiredArgsConstructor//NoArgsConstructor会将Data生成的有参构造函数删除，这里又让他生成了
@NoArgsConstructor(access = AccessLevel.PRIVATE,force=true)//jpa需要一个无参构造函数，但不准备用它，接入属性为私有，又因为final属性，所以true，这样的话id=null，name=null
@Entity
public class Ingredient {

    @Id
    private final String id;
    private final String name;
    private final Type type;
        public  static  enum Type{
                WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
        }
}
