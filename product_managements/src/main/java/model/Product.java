package model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class Product {
    private int id;
    @NotEmpty(message = "Name not empty")
    @Length(min = 2, max = 50, message = "Lenght of Name form 2 - 50 character ")
    private String name;
    private int idcategory;
    @Min(1)
    @Max(1000)
    private int quantity;
    @Min(100)
    @Max(10000000)
    private BigDecimal price;
    private String urlImage;


    public Product(int id, String name, int idcategory, int quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.idcategory = idcategory;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(int id, String name, int idcategory, int quantity, BigDecimal price, String urlImage) {
        this.id = id;
        this.name = name;
        this.idcategory = idcategory;
        this.quantity = quantity;
        this.price = price;
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Product() {
    }

    public Product(String name, int idcategory, int quantity, BigDecimal price) {
        this.name = name;
        this.idcategory = idcategory;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(int id, String name, int quantity, BigDecimal price, int idcategory) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.idcategory = idcategory;
    }

    @Min(1000)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(int idcategory) {
        this.idcategory = idcategory;
    }

    @Min(1)
    @Max(10000000)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
      return  String.format("%s %s %s %s %.3f", id, name, idcategory, quantity, price);
    }

}
