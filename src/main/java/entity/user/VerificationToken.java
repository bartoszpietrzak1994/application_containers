package main.java.entity.user;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
public class VerificationToken
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate;

    public VerificationToken() {}

    public VerificationToken(User user, String token)
    {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate();
    }

    private Date calculateExpiryDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, 1440);
        return new Date(calendar.getTime().getTime());
    }

    public Long getId()
    {
        return id;
    }

    public String getToken()
    {
        return token;
    }

    public User getUser()
    {
        return user;
    }

    public Date getExpiryDate()
    {
        return expiryDate;
    }
}

