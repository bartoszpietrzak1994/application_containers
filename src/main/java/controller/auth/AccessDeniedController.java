package main.java.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccessDeniedController
{
    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String registerForm()
    {
        return "static/html/access_denied";
    }
}
