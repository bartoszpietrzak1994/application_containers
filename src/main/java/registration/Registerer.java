package main.java.registration;

import main.java.entity.User;
import main.java.entity.UserRole;
import main.java.repository.UserRepository;
import main.java.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
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
    public void register(String email, String password, String roleName)
    {
        User user = new User();

        user.setEmail(email);
        user.setPassword(this.hashPassword(password));
        user.getUserRoles().add(this.fetchUserRole(roleName));

        userRepository.saveAndFlush(user);
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