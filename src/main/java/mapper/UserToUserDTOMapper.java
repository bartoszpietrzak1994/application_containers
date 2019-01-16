package main.java.mapper;

import main.java.entity.user.User;
import main.java.model.user.UserDTO;

public class UserToUserDTOMapper
{
    public static UserDTO toUserDTO(User user)
    {
        UserDTO userDTO = new UserDTO();

        userDTO.setEmail(user.getEmail());
        userDTO.setEnabled(user.isEnabled());
        userDTO.setRoleName(UserRoleToUserRoleDTOMapper.toUserRoleDTO(user.getUserRoles()).getRoleName());

        return userDTO;
    }
}
