package com.example.tacocloud.dao.Interface;

import com.example.tacocloud.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

//extends继承父类
public interface IngredientRepository  extends CrudRepository<Ingredient,String> {//存储库要保存的实体类型是ingredient，存储库要保留的实体Id

}
