package main.java.controller.auth.admin;

import main.java.registration.Registerer;
import main.java.registration.captcha.ReCaptchaChecker;
import main.java.sender.VerificationTokenSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminRegisterController
{
    private Registerer registerer;
    private VerificationTokenSender verificationTokenSender;
    private ReCaptchaChecker reCaptchaChecker;

    @Autowired
    public AdminRegisterController(
            Registerer registerer,
            VerificationTokenSender verificationTokenSender,
            ReCaptchaChecker reCaptchaChecker
    ) {
        this.registerer = registerer;
        this.verificationTokenSender = verificationTokenSender;
        this.reCaptchaChecker = reCaptchaChecker;
    }

    @RequestMapping(value = "/admin/register", method = RequestMethod.GET)
    public String registerForm()
    {
        return "admin/register";
    }

    @RequestMapping(value = "/admin/register", method = RequestMethod.POST)
    public String register(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("g-recaptcha-response") String captchaResponse
    ) {
        if(!this.reCaptchaChecker.verifyResponse(captchaResponse))
        {
            return "admin_register";
        }

        this.registerer.register(email, password, "ROLE_ADMIN", true);

        return "admin/login";
    }
}
