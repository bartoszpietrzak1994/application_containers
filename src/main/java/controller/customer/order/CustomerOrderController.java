package main.java.controller.customer.order;

import main.java.creator.OrderCreator;
import main.java.entity.order.Order;
import main.java.entity.user.User;
import main.java.mapper.OrderToOrderDTOMapper;
import main.java.provider.CurrentUserProvider;
import main.java.repository.order.OrderRepository;
import main.java.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CustomerOrderController
{
    private OrderRepository orderRepository;
    private CurrentUserProvider currentUserProvider;
    private UserRepository userRepository;
    private OrderCreator orderCreator;

    @Autowired
    public CustomerOrderController(
            OrderRepository orderRepository,
            CurrentUserProvider currentUserProvider,
            UserRepository userRepository,
            OrderCreator orderCreator
    ) {
        this.orderRepository = orderRepository;
        this.currentUserProvider = currentUserProvider;
        this.userRepository = userRepository;
        this.orderCreator = orderCreator;
    }

    @RequestMapping(value = "/shop/orders/place", method = RequestMethod.POST)
    public String placeOrder(Model model, @RequestBody String productName)
    {
        User user = userRepository.findUserByEmail(currentUserProvider.getCurrentlyLoggedUsersEmail());

        boolean isSuccessful = true;

        try
        {
            orderCreator.placeOrder(productName.split("=")[1], user);
        }
        catch (Exception exception)
        {
            isSuccessful = false;
        }

        model.addAttribute("isSuccessful", isSuccessful);

        return "redirect:/shop/orders/all";
    }

    @RequestMapping(value = "/shop/orders/number/{orderNumber}/details", method = RequestMethod.GET)
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

    @RequestMapping(value = "/shop/orders/all", method = RequestMethod.GET)
    public String getUsersOrders(Model model)
    {
        String currentlyLoggedUsersEmail = getCurrentlyLoggedInUserEmail();

        User userByEmail = userRepository.findUserByEmail(currentlyLoggedUsersEmail);

        List<Order> ordersByUser = orderRepository.findOrdersByUser(userByEmail);

        model.addAttribute("orders",
                ordersByUser.stream().map(OrderToOrderDTOMapper::toOrderDTO).collect(Collectors.toList())
        );

        return "user/orders";
    }

    private String getCurrentlyLoggedInUserEmail()
    {
        return currentUserProvider.getCurrentlyLoggedUsersEmail();
    }
}
