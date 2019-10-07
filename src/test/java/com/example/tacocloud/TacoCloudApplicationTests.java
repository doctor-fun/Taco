package com.example.tacocloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) //spring的测试运行器，以匹配spring上下文环境
@SpringBootTest//@SpringBootTest告诉JUnit使用Spring Boot功能引导测试。
//到目前为止，将其视为等同于在main（）方法中调用SpringApplication.run（）的测试类就足够了

//这两个注解完成了加载应用上下文的过程
public class TacoCloudApplicationTests {

    @Test
    public void contextLoads() {
    }

}
