package main.java.controller.admin.user;

import main.java.mapper.UserToUserDTOMapper;
import main.java.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.stream.Collectors;

@Controller
public class UserController
{
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/admin/users/all", method = RequestMethod.GET)
    public String getAllUsers(Model model)
    {
        model.addAttribute(
                "users",
                userRepository.findAll().stream().map(UserToUserDTOMapper::toUserDTO).collect(Collectors.toList()))
        ;

        return "admin/users";
    }
}
