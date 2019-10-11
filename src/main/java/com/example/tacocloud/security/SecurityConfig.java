package com.example.tacocloud.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    No matter which user store you choose, you can configure it by overriding a configure()
//    method defined in the WebSecurityConfigurerAdapter configuration base class. To
//    get started, you’ll add the following method override to the SecurityConfig class:


    @Override
    //在这个类里面指定如何查找用户 配置权限模块
    protected  void configure(AuthenticationManagerBuilder auth) throws  Exception{
       //在内存内生成用户，写着玩的
        auth.inMemoryAuthentication().withUser("buzz").password("infinity").authorities("ROLE_User").and()
                .withUser("woody").password("bullseye")
                .authorities("ROLE_USER");//授权的角色
    }
}
