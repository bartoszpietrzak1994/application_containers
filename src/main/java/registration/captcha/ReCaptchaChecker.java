package main.java.registration.captcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class ReCaptchaChecker
{
    @Value("${google.recaptcha.key.secret}")
    private String secret;

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean verifyResponse(String response)
    {
        RestTemplate restTemplate = new RestTemplate();

        CaptchaRequest captchaRequest = new CaptchaRequest(secret, response, "", GOOGLE_RECAPTCHA_VERIFY_URL);
        CaptchaResponse captchaResponse = restTemplate.getForObject(captchaRequest.getVerifyURI(), CaptchaResponse.class);

        assert captchaResponse != null;
        return Optional.ofNullable(captchaResponse.isSuccess()).orElse(false);
    }
}
