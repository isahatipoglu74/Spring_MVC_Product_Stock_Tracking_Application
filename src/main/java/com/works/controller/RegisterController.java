package com.works.controller;

import com.works.entities.Customer;
import com.works.repositories.CustomerRepository;
import com.works.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    final CustomerService customerService;
    final CustomerRepository customerRepository;
        String success="";
        String error="";

    @GetMapping("/register")
    public String register(Model model){
    model.addAttribute("success",success);
    model.addAttribute("error",error);
    success="";
    error="";
        return "register";
    }
    @PostMapping("/customerRegister")
    public String customerRegister(Customer customer){
         Customer c= customerService.save(customer);
            if(c==null && c.getCid()==null){
                error="Böyle bir mail adresi daha önceden kayıtlı";
            }else if (c!=null && c.getCid()>0){
                success="Kayıt işlemi başarılı";
            }
         return "redirect:/register";
    }

}
