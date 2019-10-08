package com.example.tacocloud.model;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class Order {
    @NotBlank(message = "Name is required")
    private  String name;
    @NotBlank(message = "Street is required")
    private String street;
    @NotBlank(message="City is required")
    private  String city;
    @NotBlank(message="State is required")
    private String state;
    @NotBlank(message="Zip code is required")
    private String zip;
    @CreditCardNumber(message="Not a valid credit card number")//This
//    annotation declares that the property’s value must be a valid credit card number that
//    passes the Luhn algorithm check
    private String ccNumber;

    //日期0*/**为主
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",message="Must be formatted MM/YY")
    private String ccExpiration;


    //3位有效数字，没有小数点？
    @Digits(integer = 3,fraction =0,message = "Invalid CVV")
    private String ccCVV;
}
