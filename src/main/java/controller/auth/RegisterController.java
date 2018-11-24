package main.java.controller.auth;

import main.java.registration.Registerer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController
{
    private Registerer registerer;

    @Autowired
    public RegisterController(Registerer registerer)
    {
        this.registerer = registerer;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm()
    {
        return "static/html/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("email") String email, @RequestParam("password") String password)
    {
        this.registerer.register(email, password, "ROLE_USER");

        return "static/html/login";
    }
}
