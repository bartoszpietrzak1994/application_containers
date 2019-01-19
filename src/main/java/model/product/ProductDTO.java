package main.java.model.product;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ProductDTO
{
    @NotNull(message = "Event name cannot be empty")
    @NotBlank(message = "Event name cannot be empty")
    private String name;

    @NotNull(message = "Event price cannot be empty")
    @Min(value = 0, message = "Price has to be a positive number")
    private double price;

    @NotNull(message = "Event date cannot be empty")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
