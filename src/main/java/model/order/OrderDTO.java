package main.java.model.order;

import main.java.model.user.UserDTO;

import java.util.List;

public class OrderDTO
{
    private List<OrderItemDTO> orderItems;

    private String number;

    private UserDTO userDTO;

    public List<OrderItemDTO> getOrderItems()
    {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems)
    {
        this.orderItems = orderItems;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public UserDTO getUserDTO()
    {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO)
    {
        this.userDTO = userDTO;
    }
}
