package main.java.creator;

import main.java.entity.product.Product;
import main.java.model.product.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductCreator
{
    public Product fromProductDTO(ProductDTO productDTO)
    {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDate(new java.sql.Date(productDTO.getDate().getTime()));

        return product;
    }
}
