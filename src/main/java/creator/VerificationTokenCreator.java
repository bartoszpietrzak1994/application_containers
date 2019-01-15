package main.java.creator;

import main.java.entity.user.User;
import main.java.entity.user.VerificationToken;
import main.java.repository.user.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerificationTokenCreator
{
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenCreator(VerificationTokenRepository verificationTokenRepository)
    {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public void create(User user, String token)
    {
        VerificationToken verificationToken = new VerificationToken(user, token);

        this.verificationTokenRepository.saveAndFlush(verificationToken);
    }
}
