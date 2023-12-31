package com.works.controller;

import com.works.entities.Product;
import com.works.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    final ProductService productService;

    @GetMapping("/dashboard")
    public String dashboard(Model model){
    model.addAttribute("products",productService.allProduct());

        return "dashboard";
    }
    @PostMapping("/addProduct")
    public String addProduct(Product product){
        productService.save(product);
       return "redirect:/dashboard";
    }

    @GetMapping("/productDelete/{stpid}")
    public String productDelete(@PathVariable String stpid){
       boolean status= productService.productDelete(stpid);
        System.out.println("Status : "+status);
        return "redirect:/dashboard";
    }


}
