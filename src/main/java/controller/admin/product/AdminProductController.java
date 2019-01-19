package main.java.controller.admin.product;

import main.java.creator.ProductCreator;
import main.java.mapper.ProductToProductDTOMapper;
import main.java.model.product.ProductDTO;
import main.java.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
public class AdminProductController
{
    private ProductRepository productRepository;
    private ProductCreator productCreator;

    @Autowired
    public AdminProductController(ProductRepository productRepository, ProductCreator productCreator)
    {
        this.productRepository = productRepository;
        this.productCreator = productCreator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("dd-MM-yyyy"), true, 10));
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
    public String addProductTemplate(Model model)
    {
        model.addAttribute("product", new ProductDTO());
        return "admin/add_product";
    }

    @RequestMapping(value = "/admin/products/add", method = RequestMethod.POST)
    public String addProduct(
            @Valid ProductDTO productDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors())
        {
            model.addAttribute("isSuccessful", false);
            model.addAttribute("validationMessage", bindingResult.getAllErrors().get(0).getDefaultMessage());
            model.addAttribute("product", new ProductDTO());

            return "admin/add_product";
        }

        model.addAttribute("isSuccessful", true);

        this.productRepository.saveAndFlush(this.productCreator.fromProductDTO(productDTO));

        return "redirect:/admin/products/all";
    }
}
