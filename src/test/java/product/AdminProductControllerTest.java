package test.java.product;

import main.java.model.product.ProductDTO;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import test.java.BaseFunctionalTest;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminProductControllerTest extends BaseFunctionalTest
{
    @Test
    @WithMockUser(username = "sample@gmail.com", password = "kapj", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    public void getProductsTest() throws Exception
    {
        this.mockMvc.perform(get("/admin/products/all").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("products", instanceOf(List.class)))
        ;
    }

    @Test
    @WithMockUser(username = "sample@gmail.com", password = "kapj", authorities = {"ROLE_USER"})
    public void getProductsTestForUser() throws Exception
    {
        this.mockMvc.perform(get("/admin/products/all").with(csrf()))
                .andExpect(status().is4xxClientError())
        ;
    }

    @Test
    @WithMockUser(username = "sample@gmail.com", password = "kapj", authorities = {"ROLE_ADMIN", "ROLE_USER"})
    public void addNewProductValidation() throws Exception
    {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(null);
        productDTO.setPrice(10);
        productDTO.setDate(new Date());

        this.mockMvc.perform(post("/admin/products/add", productDTO).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isSuccessful", is(false)))
        ;
    }
}
