package main.java.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminLoginController
{
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm()
    {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login()
    {

        return "welcome";
    }
}
