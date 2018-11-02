package main.java.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerLoginController
{
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm()
    {
        return "static/html/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login()
    {
        return "static/html/welcome";
    }
}
