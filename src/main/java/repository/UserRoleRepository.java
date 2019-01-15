package main.java.repository;

import main.java.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>
{
    UserRole findUserRoleByRoleName(String roleName);
}
