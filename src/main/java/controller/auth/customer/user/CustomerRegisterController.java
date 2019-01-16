package main.java.controller.auth.customer.user;

import main.java.entity.user.User;
import main.java.registration.Registerer;
import main.java.registration.captcha.ReCaptchaChecker;
import main.java.sender.VerificationTokenSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerRegisterController
{
    private Registerer registerer;
    private VerificationTokenSender verificationTokenSender;
    private ReCaptchaChecker reCaptchaChecker;

    @Autowired
    public CustomerRegisterController(
            Registerer registerer,
            VerificationTokenSender verificationTokenSender,
            ReCaptchaChecker reCaptchaChecker
    ) {
        this.registerer = registerer;
        this.verificationTokenSender = verificationTokenSender;
        this.reCaptchaChecker = reCaptchaChecker;
    }

    @RequestMapping(value = "/shop/register", method = RequestMethod.GET)
    public String registerForm()
    {
        return "user/register";
    }

    @RequestMapping(value = "/shop/register", method = RequestMethod.POST)
    public String register(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("g-recaptcha-response") String captchaResponse
    ) {
        if(!this.reCaptchaChecker.verifyResponse(captchaResponse))
        {
            return "user/register";
        }

        User user = this.registerer.register(email, password, "ROLE_USER", false);

        verificationTokenSender.generateAndSendTokenForUser(user);

        return "user/login";
    }
}
