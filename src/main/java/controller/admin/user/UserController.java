package main.java.controller.admin.user;

import main.java.mapper.UserToUserDTOMapper;
import main.java.model.user.UserDTO;
import main.java.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
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

    @RequestMapping(value = "/admin/users/json/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<List> getAllUsersAsJson()
    {
        List<UserDTO> users = this.userRepository.findAll().stream().map(UserToUserDTOMapper::toUserDTO).collect(Collectors.toList());;

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
