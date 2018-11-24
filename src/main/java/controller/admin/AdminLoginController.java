package main.java.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminLoginController
{
    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String loginForm()
    {
        return "static/html/admin_index";
    }

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public String login()
    {
        return "static/html/welcome";
    }
}
