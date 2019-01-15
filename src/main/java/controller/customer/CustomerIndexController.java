package main.java.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerIndexController
{
    @RequestMapping(value = "/shop/welcome", method = { RequestMethod.GET })
    public String welcome()
    {
        return "user/index";
    }
}
