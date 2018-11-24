package main.java.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController
{
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm()
    {
        return "static/html/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register()
    {
        return "static/html/login";
    }
}
