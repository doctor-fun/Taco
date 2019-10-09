package com.example.tacocloud.controller;


import com.example.tacocloud.dao.Interface.IngredientRepository;
import com.example.tacocloud.model.Ingredient.Type;
import com.example.tacocloud.model.Ingredient;
import com.example.tacocloud.model.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import javax.validation.Valid;


@Slf4j//@Slf4j , is a Lombok-provided annotation that, at runtime,
//will automatically generate an SLF4J (Simple Logging Facade for Java, https://www
//        .slf4j.org/) Logger in the class.
//等同于private static final org.slf4j.Logger log =
//org.slf4j.LoggerFactory.getLogger(DesignTacoController.class)
@Controller
@RequestMapping("/design")
@SessionAttributes("order")//这个注解干嘛的？
public class DesignTacoController {
    private  final IngredientRepository ingredientRepo;


    @Autowired
    //使用autowired开启自动注入功能，将单列的ingredientRepository注入到这个controller的变量当中
    public DesignTacoController(IngredientRepository ingredientRepo){
        this.ingredientRepo=ingredientRepo;
    }

    @ModelAttribute
    //设定model类的属性，一般是从数据库中找一些数据，添加给model
    public void addIngredientsToModel(Model model) {
        //model是个map的接口实现
        List<Ingredient> ingredients = Arrays.asList(//这里在模拟从数据库取的数据
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );
        Type[] types = Ingredient.Type.values();//取出枚举的所有值做成列表组合（Type是个枚举类型）
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));//将上面的每条记录根据type分类存进map中
        }
    }



    @GetMapping
    public String ShowDesignForm(Model model){
        List<Ingredient> ingredients=new ArrayList<>();
        //这一句有点懵
        ingredientRepo.findAll().forEach(i->ingredients.add(i));

        Type[] types=Ingredient.Type.values();
        for(Type type:types){
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        return "design";//view的名字，这个view为浏览器渲染model 一般是resource/templates下的名为design.html的模板文件
    }

    @PostMapping
    //接收用户对设计的要求
    public String processDesign(@Valid @ModelAttribute("design")  Taco design, Errors errors, Model model){
        if(errors.hasErrors()){
            return "design";
        }
        log.info("Processing design: " + design);
        return "redirect:/orders/current";
        //页面的Name存到string,其他成分存到List中，这两个一和就是Taco
        //收到用户的设计内容后，转发到orders/current的controller用于订单管理
    }

    private  List<Ingredient> filterByType(
            List<Ingredient> ingredients,Type type){
        return  ingredients.
                stream()
                .filter(x->x.getType().equals(type))
                .collect(Collectors.toList());

    }

}
