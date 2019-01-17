package main.java.controller.customer.order;

import main.java.entity.order.Order;
import main.java.entity.user.User;
import main.java.mapper.OrderToOrderDTOMapper;
import main.java.provider.CurrentUserProvider;
import main.java.repository.order.OrderRepository;
import main.java.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class CustomerOrderController
{
    private OrderRepository orderRepository;
    private CurrentUserProvider currentUserProvider;
    private UserRepository userRepository;

    @Autowired
    public CustomerOrderController(
            OrderRepository orderRepository,
            CurrentUserProvider currentUserProvider,
            UserRepository userRepository
    ) {
        this.orderRepository = orderRepository;
        this.currentUserProvider = currentUserProvider;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/shop/orders/place", method = RequestMethod.POST)
    public String placeOrder(@ModelAttribute("order") Order order)
    {
        order.setUser(userRepository.findUserByEmail(getCurrentlyLoggedInUserEmail()));

        orderRepository.saveAndFlush(order);

        return "user/index";
    }

    @RequestMapping(value = "/shop/orders/{orderNumber}/details", method = RequestMethod.GET)
    public String orderDetails(@PathVariable String orderNumber, Model model)
    {
        String currentlyLoggedUsersEmail = getCurrentlyLoggedInUserEmail();

        Order orderByNumber = orderRepository.findOrderByNumber(orderNumber);

        if (orderByNumber == null)
        {
            return "access_denied";
        }

        if (!orderByNumber.getUser().getEmail().equals(currentlyLoggedUsersEmail))
        {
            return "access_denied";
        }

        model.addAttribute("order", OrderToOrderDTOMapper.toOrderDTO(orderByNumber));

        return "user/order_details";
    }

    @RequestMapping(value = "/shop/orders/", method = RequestMethod.GET)
    public String getUsersOrders(Model model)
    {
        String currentlyLoggedUsersEmail = getCurrentlyLoggedInUserEmail();

        User userByEmail = userRepository.findUserByEmail(currentlyLoggedUsersEmail);

        List<Order> ordersByUser = orderRepository.findOrdersByUser(userByEmail);

        model.addAttribute("orders", ordersByUser);

        return "user/orders";
    }

    private String getCurrentlyLoggedInUserEmail()
    {
        return currentUserProvider.getCurrentlyLoggedUsersEmail();
    }
}
