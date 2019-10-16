package com.example.tacocloud.controller.Discount;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/discounts")
public class DiscountController {
    private DiscountCodeProps discountProps;

    public DiscountController(DiscountCodeProps discountCodeProps){
        this.discountProps=discountProps;
    }
    @GetMapping
    public  String displayDiscountCodes(Model model){
        Map<String,Integer> codes=discountProps.getCodes();
        model.addAttribute("code",codes);
        return "discountList";
    }
}
