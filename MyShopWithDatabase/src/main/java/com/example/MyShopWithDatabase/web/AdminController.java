package com.example.MyShopWithDatabase.web;

import com.example.MyShopWithDatabase.DAO.CartDAO;
import com.example.MyShopWithDatabase.DAO.CategoryDAO;
import com.example.MyShopWithDatabase.DAO.ProductDAO;
import com.example.MyShopWithDatabase.model.Category;
import com.example.MyShopWithDatabase.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

@Controller
public class AdminController {
    private CartDAO cartDAO;
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    public AdminController(CartDAO cartDAO, ProductDAO productDAO, CategoryDAO categoryDAO) {
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
    }

    @GetMapping("/admin")
    protected String adminPanel(Model model) {
        model.addAttribute("cart", cartDAO);
        model.addAttribute("products", productDAO.all());
        return "admin";
    }

    @GetMapping("/admin/list")
    protected String editProduct(Model model) {
        model.addAttribute("products", productDAO.all());
        return "admin_list";
    }

    @GetMapping("/admin/add_product")
    protected String addProduct(Model model) {
        model.addAttribute("products", productDAO.all());
        model.addAttribute("categories", categoryDAO.getAllCategories());
        model.addAttribute("newProduct", new Product("name", "description", BigDecimal.ZERO, categoryDAO.getCategoryByName("category")));
        return "admin_add_product";
    }

    @PostMapping("/admin/add_product/{name}")
    public String addProductConfirm(@ModelAttribute Product newProduct, @PathVariable("name") String name, Model model) {
            model.addAttribute("newProduct", newProduct);
            model.addAttribute("products", productDAO.all());

            if (productDAO.byName(newProduct.getName()) == null) {
                newProduct = new Product(newProduct.getName(), newProduct.getDescription(), newProduct.getPrice(), newProduct.getCategory());
                productDAO.addProduct(newProduct);
                return "admin_product_added";
            } else {
                return "admin_product_not_added";
            }
    }

    @GetMapping("/admin/categories")
    protected String editCategory(Model model) {
        model.addAttribute("categories", categoryDAO.getAllCategories());
        return "admin_categories";
    }

    @GetMapping("/admin/category/{name}/edit")
    protected String editCategory(@PathVariable("name") String name, Model model) {
        model.addAttribute("category", categoryDAO.getCategoryByName(name));
        model.addAttribute("editCategory", new Category(name));
        model.addAttribute("categories", categoryDAO.getAllCategories());
        model.addAttribute("productsByCategory", productDAO.byCategory(name));
        return "admin_category_edit";
    }

    @PostMapping("/admin/category/{name}/update")
    public String confirmChange(@ModelAttribute Category editCategory, @PathVariable("name") String name, Model model) {
            model.addAttribute("editCategory", editCategory);
            model.addAttribute("categories", categoryDAO.getAllCategories());
            categoryDAO.replaceCategory(categoryDAO.byName(name),editCategory);
            return "admin_category_edit_succes";
    }

    @GetMapping("/admin/add_category")
    protected String addCategory(Model model) {
        model.addAttribute("newCategory", new Category("categoryName"));
        return "admin_add_category";
    }
    @PostMapping("/admin/add_category/{name}")
    public String addCategoryConfirm(@ModelAttribute Category newCategory, @PathVariable("name") String name, Model model) {
        model.addAttribute("newCategory", newCategory);

        if (categoryDAO.byName(newCategory.getName()) == null) {
            newCategory = new Category(newCategory.getName());
            categoryDAO.addCategory(newCategory);
            return "admin_category_added";
        } else {
            return "admin_category_not_added";
        }
    }

}
