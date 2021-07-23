package com.alaiah.shopify.controller;


import com.alaiah.shopify.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/generic")
public class CustomerController {


    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/isExistingCustomer")
    public boolean isExistingCustomer(String email) {

        return this.customerService.isExistingCustomer(email);
    }


}
