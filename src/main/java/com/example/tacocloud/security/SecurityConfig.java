package com.example.tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;


import javax.sql.DataSource;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
//关于用户的一些常用的查询都会自动生成
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //a configure()
//    method defined in the WebSecurityConfigurerAdapter configuration base cla ss . To
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

//    @Override
//    //将用户信息配置到数据库，进行联动
//    protected void configure (AuthenticationManagerBuilder auth) throws Exception{
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery(
//                        "select username,password,enabled from Users "+"where username=?")
//                .authoritiesByUsernameQuery("select username,authority from UserAuthorities "+ "where username=?")
//                .passwordEncoder(new SCryptPasswordEncoder);
//                //说明还有张授权表
//                //团队表 group id,group name, group授权
//                //用户权限表 ：用户名，授权等级
//                //用户个人信息表：用户名，密码，使用状态（是否已经被授权或者其他字段）
//    }

    @Qualifier("userRepositoryUserDetailsService")//因为可能userdetailsService有多个实现类，这里注入service的时候要表明实现类是哪个
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean//密码要加密保存//定义了bean，那么会单例创建放进上下文中
    public PasswordEncoder encoder(){
        return new StandardPasswordEncoder("53cr3t");
    }


    @Override//这是个配置类，将各种信息配置进Spring Security
    protected  void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)//将实际的实现类通过这一方法注入到Security实现方法中
        .passwordEncoder(encoder());//因为encoder方法的注解是Bean，所以返回的是上下文的StandardPasswordEncoder（编码器）
    }

    //这些规则的顺序很重要。 首先声明的安全规则优先于下层声明的安全规则。 如果要交换这两个安全规则的顺序，则所有请求都将被应用allowAll（）； / design和/ orders请求的规则将无效。
//     Requests for /design and /orders should be for users with a granted authority
//    of ROLE_USER .
//             All requests should be permitted to all users.

    @Override
    protected  void configure(HttpSecurity http ) throws  Exception{
        http.authorizeRequests()
                .antMatchers("/design","/orders").access("hasRole(ROLE_USER)")//这两个链接应该要有ROLE_USER的角色
                .antMatchers("/","/**").access("permitAll()")


//        关于此登录页面的主要注意事项是其登录的路径以及用户名和密码字段的名称。
//        默认情况下，Spring Security在/
//        login侦听登录请求，并期望将username和password字段命名为username和password。
//        但是，这是可配置的。 例如，以下配置自定义路径和字段名称
                .and()      //and（）方法表示您已完成授权配置，并准备应用一些其他HTTP配置。
                .formLogin()  //用来替换spring security的login页面
                .loginPage("/login")//页面名称为login.html
//                .loginProcessingUrl("/authenticate")//用/authenticate访问/login
//                .usernameParameter("user")//数据库的用户名字段改成user
//                .passwordParameter("pwd");//数据库的密码字段名改成pwd
                .and()
                .logout()
                .logoutSuccessUrl("/")

                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**")



    }

}
