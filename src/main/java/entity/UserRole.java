package main.java.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRole
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    public Long getId()
    {
        return id;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }
}
