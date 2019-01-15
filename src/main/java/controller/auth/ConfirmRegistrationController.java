package main.java.controller.auth;

import main.java.entity.user.User;
import main.java.entity.user.VerificationToken;
import main.java.repository.UserRepository;
import main.java.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Calendar;

@Controller
public class ConfirmRegistrationController
{
    private VerificationTokenRepository verificationTokenRepository;
    private UserRepository userRepository;

    @Autowired
    public ConfirmRegistrationController(
            VerificationTokenRepository verificationTokenRepository,
            UserRepository userRepository
    ) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/registrationConfirm/{token}", method = RequestMethod.GET)
    public String confirmRegistration(@PathVariable("token") String token)
    {
        VerificationToken verificationToken = this.verificationTokenRepository.findVerificationTokenByToken(token);

        if (verificationToken == null)
        {
            return "access_denied";
        }

        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0)
        {
            return "access_denied";
        }

        User user = verificationToken.getUser();

        user.setEnabled(true);

        this.userRepository.saveAndFlush(user);

        return "login";
    }
}
