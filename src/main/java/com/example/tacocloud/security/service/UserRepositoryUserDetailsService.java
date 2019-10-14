package com.example.tacocloud.security.service;

import com.example.tacocloud.dao.Interface.UserRepository;
import com.example.tacocloud.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//UserDetailsService的
//public interface UserDetailsService {
//UserDetails loadUserByUsername(String username)
//throws UsernameNotFoundException;
//}
//因为可能userdetailsService有多个实现类，这里注入service的时候要表明实现类是哪个
@Service("userRepositoryUserDetailsService")//也会自动开启此bean的扫描
public class UserRepositoryUserDetailsService implements UserDetailsService {
    private UserRepository userRepo;

    @Autowired//注意是依赖注入到构造方法中
    public UserRepositoryUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByUsername(username);
        if(user!=null){
            return  user;
        }
        throw new UsernameNotFoundException("User '"+username+"' not found");

    }
}
