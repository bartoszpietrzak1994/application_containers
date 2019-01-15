package main.java.repository.order;

import main.java.entity.order.Order;
import main.java.entity.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>
{
    List<OrderItem> findOrderItemsByOrder(Order order);
}
