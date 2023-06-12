package com.works.controller;

import com.works.entities.Customer;
import com.works.repositories.CustomerRepository;
import com.works.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class LoginController {

    final CustomerService customerService;
    final CustomerRepository customerRepository;
    final HttpServletRequest request;
    final HttpServletResponse response;
    String success="";
    String error="";

    @GetMapping("/")
    public String login(Model model){
        model.addAttribute("success",success);
        model.addAttribute("error",error);
        success="";
        error="";
        return "login";
    }

    @PostMapping("/loginUser")
    public String loginUser(Customer customer){
        Customer c = customerService.login(customer.getEmail(),customer.getPassword());
        if (c == null) {
            Customer control = customerService.loginUser(customer.getEmail());
            if (control == null) {
                error = "E-mail Hatalı";
                return "redirect:/";
            }else {
                error = "Şifre Hatalı";
                return "redirect:/";
            }
        }

        request.getSession().setAttribute("user",c);
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(){
        request.getSession().removeAttribute("user");//session'ı kurulmuş olan kulanıcının session'ını program kısmında yok eder
        Cookie cookie = new Cookie("user", "");
        cookie.setMaxAge(0); //0 . saniyede logout olur olmaz cookieler silinir.
        response.addCookie(cookie);
        return "redirect:/";
    }
}
