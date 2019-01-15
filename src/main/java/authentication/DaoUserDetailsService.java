package main.java.authentication;

import main.java.entity.user.User;
import main.java.model.user.DaoUserDetails;
import main.java.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DaoUserDetailsService implements UserDetailsService
{
    private UserRepository userRepository;

    @Autowired
    public DaoUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        User user = userRepository.findUserByEmail(email);
        if (user == null)
        {
            throw new UsernameNotFoundException(email);
        }

        return new DaoUserDetails(user);
    }
}
