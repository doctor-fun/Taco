package com.example.tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//关于用户的一些常用的查询都会自动生成
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    No matter which user store you choose, you can configure it by overriding a configure()
//    method defined in the WebSecurityConfigurerAdapter configuration base class. To
//    get started, you’ll add the following method override to the SecurityConfig class:


//    @Override
//    //在这个类里面指定如何查找用户 配置权限模块
//    protected  void configure(AuthenticationManagerBuilder auth) throws  Exception{
//       //在内存内生成用户，写着玩的
//        auth.inMemoryAuthentication().withUser("buzz").password("infinity").authorities("ROLE_User").and()
//                .withUser("woody").password("bullseye")
//                .authorities("ROLE_USER");//授权的角色
//    }
    @Autowired
    DataSource dataSource;

    @Override
    //将用户信息配置到数据库，进行联动
    protected void configure (AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username,password,enabled from Users "+"where username=?")
                .authoritiesByUsernameQuery("select username,authority from UserAuthorities "+ "where username=?");
                //说明还有张授权表
                //团队表 group id,group name, group授权
                //用户权限表 ：用户名，授权等级
                //用户个人信息表：用户名，密码，使用状态（是否已经被授权或者其他字段）
    }
}
