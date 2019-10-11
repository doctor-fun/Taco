package com.example.tacocloud.dao.Interface;

import com.example.tacocloud.model.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco,Long> {

}
