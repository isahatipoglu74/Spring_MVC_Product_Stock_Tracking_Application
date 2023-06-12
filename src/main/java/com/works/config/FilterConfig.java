package com.works.config;

import com.works.entities.Customer;
import com.works.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Configuration
public class FilterConfig implements Filter {

    final CustomerService customerService;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String url = req.getRequestURI();
        System.out.println(url);
        String freeurls[] = {"/","/loginUser","/register","/customerRegister"};
        boolean loginStatus = true;
        for (String item : freeurls){
            if (url.equals(item)){
                loginStatus = false;
                break;
            }
        }
        if (loginStatus){
            // Cookie Control
            if (req.getCookies() != null){
                Cookie[] cookies = req.getCookies();
                for (Cookie cookie : cookies){
                    if (cookie.getName().equals("user")){
                        Long val =Long.parseLong(cookie.getValue());
                        Customer c = customerService.single(val);
                        if (c != null){
                            req.getSession().setAttribute("user",c);
                            break;
                        }
                    }
                }
            }
            // session control
            boolean status = req.getSession().getAttribute("user")==null;
            if (status){
                res.sendRedirect("/");
            }else {
                Customer c = (Customer) req.getSession().getAttribute("user");
                req.setAttribute("user",c);
                filterChain.doFilter(req,response);
            }
        }else {
            filterChain.doFilter(req,res);
        }
    }
}
