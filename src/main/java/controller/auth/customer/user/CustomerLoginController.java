package main.java.controller.auth.customer.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerLoginController
{
    @RequestMapping(value = {"/shop/login", "/"}, method = RequestMethod.GET)
    public String loginForm()
    {
        return "user/login";
    }
}
