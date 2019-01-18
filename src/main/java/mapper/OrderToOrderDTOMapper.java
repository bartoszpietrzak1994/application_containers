package main.java.mapper;

import main.java.entity.order.Order;
import main.java.entity.order.OrderItem;
import main.java.model.order.OrderDTO;

import java.util.Date;
import java.util.stream.Collectors;

public class OrderToOrderDTOMapper
{
    public static OrderDTO toOrderDTO(Order order)
    {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setNumber(order.getNumber());
        orderDTO.setUserEmail(order.getUser().getEmail());
        orderDTO.setDate(new Date(order.getOrderItems().iterator().next().getProduct().getDate().getTime()));
        orderDTO.setProductName(order.getOrderItems().iterator().next().getProduct().getName());

        double total = 0;

        for(OrderItem orderItem : order.getOrderItems())
        {
            if (orderItem.getProduct() == null)
            {
                continue;
            }

            total += orderItem.getProduct().getPrice();
        }

        orderDTO.setTotal(total);

        return orderDTO;
    }
}
