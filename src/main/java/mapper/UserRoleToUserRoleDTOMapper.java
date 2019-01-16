package main.java.mapper;

import main.java.entity.user.UserRole;
import main.java.model.user.UserRoleDTO;

import java.util.Set;

public class UserRoleToUserRoleDTOMapper
{
    public static UserRoleDTO toUserRoleDTO(Set<UserRole> userRoles)
    {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setRoleName(userRoles.iterator().next().getRoleName());

        return userRoleDTO;
    }
}
