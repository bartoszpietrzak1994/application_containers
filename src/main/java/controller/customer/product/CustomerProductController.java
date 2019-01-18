package main.java.controller.customer.product;

import main.java.mapper.ProductToProductDTOMapper;
import main.java.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.stream.Collectors;

@Controller
public class CustomerProductController
{
    private ProductRepository productRepository;

    @Autowired
    public CustomerProductController(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    @RequestMapping(value = "/shop/products/all", method = RequestMethod.GET)
    public String getAllProducts(Model model)
    {
        model.addAttribute(
                "products",
                productRepository.findAll().stream().map(ProductToProductDTOMapper::toProductDTO).collect(Collectors.toList()))
        ;

        return "redirect:user/products";
    }
}
