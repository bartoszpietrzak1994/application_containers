package main.java.mapper;

import main.java.entity.order.OrderItem;
import main.java.model.order.OrderItemDTO;

public class OrderItemToOrderItemDTOMapper
{
    public static OrderItemDTO toOrderItemDTO(OrderItem orderItem)
    {
        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setOrder(OrderToOrderDTOMapper.toOrderDTO(orderItem.getOrder()));
        orderItemDTO.setProduct(ProductToProductDTOMapper.toProductDTO(orderItem.getProduct()));
        orderItemDTO.setProductQuantity(orderItem.getProductQuantity());

        return orderItemDTO;
    }
}
