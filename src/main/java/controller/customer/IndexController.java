package main.java.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController
{
    @RequestMapping(value = "/welcome", method = { RequestMethod.GET, RequestMethod.POST })
    public String welcome()
    {
        return "index";
    }
}
