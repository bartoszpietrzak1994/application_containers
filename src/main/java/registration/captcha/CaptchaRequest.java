package main.java.registration.captcha;

import java.net.URI;

public class CaptchaRequest
{
    private String secret;
    private String response;
    private String remoteIp;
    private String googleUrl;

    public CaptchaRequest(String secret, String response, String remoteIp, String googleUrl)
    {
        this.secret = secret;
        this.response = response;
        this.remoteIp = remoteIp;
        this.googleUrl = googleUrl;
    }

    public URI getVerifyURI()
    {
        return URI.create(
                String.format("%s?secret=%s&response=%s&remoteip=%s",
                        googleUrl,
                        secret,
                        response,
                        remoteIp)
        );
    }
}
