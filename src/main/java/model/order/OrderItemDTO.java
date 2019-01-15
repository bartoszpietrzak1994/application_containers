package main.java.model.order;

import main.java.model.product.ProductDTO;

public class OrderItemDTO
{
    private OrderDTO order;
    private ProductDTO product;
    private int productQuantity;

    public OrderDTO getOrder()
    {
        return order;
    }

    public void setOrder(OrderDTO order)
    {
        this.order = order;
    }

    public ProductDTO getProduct()
    {
        return product;
    }

    public void setProduct(ProductDTO product)
    {
        this.product = product;
    }

    public int getProductQuantity()
    {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity)
    {
        this.productQuantity = productQuantity;
    }
}
