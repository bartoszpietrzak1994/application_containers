package main.java.controller.admin.order;

import main.java.mapper.OrderToOrderDTOMapper;
import main.java.repository.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.stream.Collectors;

@Controller
public class OrderController
{
    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository)
    {
        this.orderRepository = orderRepository;
    }

    @RequestMapping(value = "/admin/orders/all", method = RequestMethod.GET)
    public String getAllOrders(Model model)
    {
        model.addAttribute(
                "orders",
                orderRepository.findAll().stream().map(OrderToOrderDTOMapper::toOrderDTO).collect(Collectors.toList()))
        ;

        return "admin/orders";
    }

    @RequestMapping(value = "/admin/orders/id/{id}")
    public String getOrderDetails(Model model, String orderId)
    {
        model.addAttribute(
                "order",
                OrderToOrderDTOMapper.toOrderDTO(this.orderRepository.getOne(Long.valueOf(orderId)))
        );

        return "admin/order_details";
    }
}
