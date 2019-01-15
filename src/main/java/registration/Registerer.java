package main.java.registration;

import main.java.entity.user.User;
import main.java.entity.user.UserRole;
import main.java.repository.UserRepository;
import main.java.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Registerer
{
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRoleRepository userRoleRepository;
    private UserRepository userRepository;

    @Autowired
    public Registerer(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserRoleRepository userRoleRepository,
            UserRepository userRepository
    ) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public User register(String email, String password, String roleName, boolean isEnabled)
    {
        User user = new User();

        user.setEmail(email.trim());
        user.setPassword(this.hashPassword(password));
        user.addUserRole(this.fetchUserRole(roleName));
        user.setEnabled(isEnabled);

        userRepository.saveAndFlush(user);

        return user;
    }

    private String hashPassword(String password)
    {
        return this.bCryptPasswordEncoder.encode(password);
    }

    private UserRole fetchUserRole(String roleName)
    {
        return this.userRoleRepository.findUserRoleByRoleName(roleName);
    }
}
