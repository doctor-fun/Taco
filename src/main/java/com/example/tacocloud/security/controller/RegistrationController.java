package com.example.tacocloud.security.controller;

import com.example.tacocloud.dao.Interface.UserRepository;
import com.example.tacocloud.security.model.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    //controller注入了passwordEncoder,等需要密码的时候使用
    public RegistrationController( UserRepository userRepo,PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder=passwordEncoder;

    }
    @GetMapping
    public String registerForm() {
        return "registration";
    }
    @PostMapping
    public String processRegistration(RegistrationForm form){
        userRepo.save(form.toUser(passwordEncoder));//form.toUser会生成一个User类，userRepo.save可以保存这个user类//controller将passwordEncoder传递给form（modeL）
        return "redirect:/login";
    }
}
