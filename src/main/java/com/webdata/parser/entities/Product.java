package com.webdata.parser.entities;


/**
 * Created by Sonik on 24.11.2017.
 */
public class Product {
    private String name;
    private String brand;
    private String color;
    private String price;
    private String initialPrice;
    private String articleID;
    private String shippingCosts;
    private Description description;

    public Product(String name, String brand, String color, String price, String initialPrice, String articleID, String shippingCosts, Description description) {
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.price = price;
        this.initialPrice = initialPrice;
        this.articleID = articleID;
        this.shippingCosts = shippingCosts;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public String getPrice() {
        return price;
    }

    public String getInitialPrice() {
        return initialPrice;
    }

    public Description getDescription() {
        return description;
    }

    public String getArticleID() {
        return articleID;
    }

    public String getShippingCosts() {
        return shippingCosts;
    }

    @Override
    public String toString() {
        return "Product:" + '\n' +
                "   Name = " + name + '\n' +
                "   Brand = " + brand + '\n' +
                "   Color = " + color + '\n' +
                "   Price = " + price + '\n' +
                "   InitialPrice = " + initialPrice + '\n' +
                "   ShippingCosts = " + shippingCosts + '\n' +
                "   ArticleID = " + articleID + '\n' +
                description.toString();
    }
}
