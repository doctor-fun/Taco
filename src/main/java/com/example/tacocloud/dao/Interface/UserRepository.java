package com.example.tacocloud.dao.Interface;

import com.example.tacocloud.security.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}