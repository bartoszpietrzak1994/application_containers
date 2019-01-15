package main.java.entity.order;

import main.java.entity.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    private List<OrderItem> orderItems;

    @Column(name = "order_number")
    private String number;

    public List<OrderItem> getOrderItems()
    {
        return orderItems;
    }

    public Long getId()
    {
        return id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setOrderItems(List<OrderItem> orderItems)
    {
        this.orderItems = orderItems;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }
}
