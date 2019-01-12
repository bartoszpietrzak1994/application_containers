package main.java.controller.auth;

import main.java.entity.User;
import main.java.registration.Registerer;
import main.java.sender.VerificationTokenSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController
{
    private Registerer registerer;
    private VerificationTokenSender verificationTokenSender;

    @Autowired
    public RegisterController(Registerer registerer, VerificationTokenSender verificationTokenSender)
    {
        this.registerer = registerer;
        this.verificationTokenSender = verificationTokenSender;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm()
    {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        User user = this.registerer.register(email, password, "ROLE_USER");

        verificationTokenSender.generateAndSendTokenForUser(user);

        return "redirect:/user-login";
    }
}
