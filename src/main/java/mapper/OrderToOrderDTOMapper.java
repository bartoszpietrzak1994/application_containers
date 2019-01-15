package main.java.mapper;

import main.java.entity.order.Order;
import main.java.model.order.OrderDTO;

import java.util.stream.Collectors;

public class OrderToOrderDTOMapper
{
    public static OrderDTO toOrderDTO(Order order)
    {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setNumber(order.getNumber());
        orderDTO.setUserDTO(UserToUserDTOMapper.toUserDTO(order.getUser()));
        orderDTO.setOrderItems(order.getOrderItems().stream().map(
                OrderItemToOrderItemDTOMapper::toOrderItemDTO).collect(Collectors.toList())
        );

        return orderDTO;
    }
}
