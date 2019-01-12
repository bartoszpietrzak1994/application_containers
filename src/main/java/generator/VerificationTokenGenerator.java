package main.java.generator;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VerificationTokenGenerator
{
    public String uuid()
    {
        return UUID.randomUUID().toString();
    }
}
