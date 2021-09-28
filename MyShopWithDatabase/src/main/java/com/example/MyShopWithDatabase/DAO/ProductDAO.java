package com.example.MyShopWithDatabase.DAO;


import com.example.MyShopWithDatabase.model.Category;
import com.example.MyShopWithDatabase.model.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ProductDAO {

    private List<Product> products = new LinkedList<Product>(Arrays.asList(
            new Product("Jabłko", "bardzo słodkie, czerwone lub zielone", new BigDecimal("25.00"), new Category("OWOCE")),
            new Product("Ziemniak", "Jabłko wśród warzyw", new BigDecimal("99.99"), new Category("WARZYWA")),
            new Product("Orzech włoski", "Dobry na pamięć", new BigDecimal("29.99"), new Category("ORZECHY")),
            new Product("Ogórek", "zielony, gruntowy", new BigDecimal("5.50"), new Category("WARZYWA"))));

    public List<Product> all() {
        return products;
    }

    public void removeProductByName(String name) {
        for (Product product : products) {
            if (name.equals(product.getName())) {
                products.remove(product);
                break;
            }
        }
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void replaceProduct(Product oldProduct, Product newProduct){
        oldProduct.setName(newProduct.getName());
        oldProduct.setDescription(newProduct.getDescription());
        oldProduct.setPrice(newProduct.getPrice());
        oldProduct.setCategory(newProduct.getCategory());
    }

    public Product byName(String name) {
        for (Product product : products) {
            if (name.equals(product.getName())) {
                return product;
            }
        }
        return null;
    }

    public List<Product> byCategory(String category) {
        LinkedList productsByCategory = new LinkedList();

        for (Product product : products) {
            if (category.equals(product.getCategory().getName())) {
                productsByCategory.add(product);
            }
        }
            return productsByCategory;
    }
}
