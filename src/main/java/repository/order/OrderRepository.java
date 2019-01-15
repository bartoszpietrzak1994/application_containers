package main.java.repository.order;

import main.java.entity.order.Order;
import main.java.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>
{
    Order findOrderByNumber(String number);
    List<Order> findOrdersByUser(User user);
}
