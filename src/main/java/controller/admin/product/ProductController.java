package main.java.controller.admin.product;

import main.java.entity.product.Product;
import main.java.mapper.ProductToProductDTOMapper;
import main.java.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.stream.Collectors;

@Controller
public class ProductController
{
    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    @RequestMapping(value = "/admin/products/all", method = RequestMethod.GET)
    public String getAllProducts(Model model)
    {
        model.addAttribute(
                "products",
                productRepository.findAll().stream().map(ProductToProductDTOMapper::toProductDTO).collect(Collectors.toList()))
        ;

        return "admin/products";
    }

    @RequestMapping(value = "/admin/products/add", method = RequestMethod.GET)
    public String addProductTemplate()
    {
        return "admin/add_product";
    }

    @RequestMapping(value = "/admin/products/add", method = RequestMethod.POST)
    public String addProduct(@RequestBody Product product)
    {
        this.productRepository.saveAndFlush(product);

        return "admin/products";
    }
}
