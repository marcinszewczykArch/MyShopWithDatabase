package com.example.MyShopWithDatabase.web;

import com.example.MyShopWithDatabase.DAO.CartDAO;
import com.example.MyShopWithDatabase.DAO.CategoryDAO;
import com.example.MyShopWithDatabase.DAO.ProductDAO;
import org.springframework.stereotype.Controller;

@Controller
public class CartController {
    private CartDAO cartDAO;
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    public CartController(CartDAO cartDAO, ProductDAO productDAO, CategoryDAO categoryDAO) {
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
    }







}
