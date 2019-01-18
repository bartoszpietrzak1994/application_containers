package main.java.creator;

import main.java.entity.order.Order;
import main.java.entity.order.OrderItem;
import main.java.entity.product.Product;
import main.java.entity.user.User;
import main.java.generator.UUIDGenerator;
import main.java.repository.order.OrderRepository;
import main.java.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCreator
{
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private UUIDGenerator uuidGenerator;

    @Autowired
    public OrderCreator(
            ProductRepository productRepository,
            OrderRepository orderRepository,
            UUIDGenerator uuidGenerator
    ) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.uuidGenerator = uuidGenerator;
    }

    public void placeOrder(String productName, User user)
    {
        Product product = productRepository.findFirstByName(productName);

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);

        Order order = new Order();
        order.setUser(user);
        order.getOrderItems().add(orderItem);
        order.setNumber(uuidGenerator.uuid());
        orderRepository.saveAndFlush(order);
    }
}
