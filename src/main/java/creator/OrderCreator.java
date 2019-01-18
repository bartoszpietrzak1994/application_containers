package main.java.creator;

import com.itextpdf.text.DocumentException;
import main.java.entity.order.Order;
import main.java.entity.order.OrderItem;
import main.java.entity.product.Product;
import main.java.entity.user.User;
import main.java.generator.UUIDGenerator;
import main.java.mapper.OrderToOrderDTOMapper;
import main.java.repository.order.OrderRepository;
import main.java.repository.product.ProductRepository;
import main.java.sender.PdfFileSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;

@Component
public class OrderCreator
{
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private UUIDGenerator uuidGenerator;
    private PdfFileSender pdfFileSender;

    @Autowired
    public OrderCreator(
            ProductRepository productRepository,
            OrderRepository orderRepository,
            UUIDGenerator uuidGenerator,
            PdfFileSender pdfFileSender
    ) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.uuidGenerator = uuidGenerator;
        this.pdfFileSender = pdfFileSender;
    }

    public void placeOrder(String productName, User user) throws FileNotFoundException, DocumentException, MessagingException
    {
        Product product = productRepository.findFirstByName(productName);

        if (product == null)
        {
            return;
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);

        Order order = new Order();

        orderItem.setOrder(order);

        order.setUser(user);
        order.getOrderItems().add(orderItem);
        order.setNumber(uuidGenerator.uuid());
        orderRepository.saveAndFlush(order);

        pdfFileSender.sendPdfFile(user.getEmail(), OrderToOrderDTOMapper.toOrderDTO(order));
    }
}
