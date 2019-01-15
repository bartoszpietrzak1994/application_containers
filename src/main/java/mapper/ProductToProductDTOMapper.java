package main.java.mapper;

import main.java.entity.product.Product;
import main.java.model.product.ProductDTO;

import java.util.Date;

public class ProductToProductDTOMapper
{
    public static ProductDTO toProductDTO(Product product)
    {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDate(new Date(product.getDate().getTime()));

        return productDTO;
    }
}
