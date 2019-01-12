package main.java.sender;

import main.java.creator.VerificationTokenCreator;
import main.java.entity.User;
import main.java.generator.VerificationTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class VerificationTokenSender
{
    private static final String REGISTRATION_CONFIRMATION_URL = "http://localhost:8080/registrationConfirm/";

    private VerificationTokenCreator verificationTokenCreator;
    private JavaMailSender mailSender;
    private VerificationTokenGenerator verificationTokenGenerator;

    @Autowired
    public VerificationTokenSender(
            VerificationTokenCreator verificationTokenCreator,
            JavaMailSender mailSender,
            VerificationTokenGenerator verificationTokenGenerator
    ) {
        this.verificationTokenCreator = verificationTokenCreator;
        this.mailSender = mailSender;
        this.verificationTokenGenerator = verificationTokenGenerator;
    }

    public void generateAndSendTokenForUser(User user)
    {
        String token = this.verificationTokenGenerator.uuid();
        verificationTokenCreator.create(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = REGISTRATION_CONFIRMATION_URL + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(confirmationUrl);
        mailSender.send(email);
    }
}
