package main.java.controller.customer.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomerOrderController
{
    @RequestMapping(value = "/shop/orders/make", method = RequestMethod.POST)
    public String makeOrder()
    {
        return "user/index";
    }
}
