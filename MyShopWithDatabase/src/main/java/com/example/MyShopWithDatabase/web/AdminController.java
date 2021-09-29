package com.example.MyShopWithDatabase.web;

import com.example.MyShopWithDatabase.DAO.CartDAO;
import com.example.MyShopWithDatabase.DAO.CategoryDAO;
import com.example.MyShopWithDatabase.DAO.ProductDAO;
import com.example.MyShopWithDatabase.Repository.CategoryRepository;
import com.example.MyShopWithDatabase.Repository.OrdersRepository;
import com.example.MyShopWithDatabase.Repository.ProductRepository;
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
    //    private CartDAO cartDAO;
    private OrdersRepository ordersRepository;
    //    private ProductDAO productDAO;
    private ProductRepository productRepository;
    //    private CategoryDAO categoryDAO;
    private CategoryRepository categoryRepository;

    public AdminController(OrdersRepository ordersRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.ordersRepository = ordersRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/admin")
    protected String adminPanel(Model model) {
        model.addAttribute("cart", ordersRepository);
        model.addAttribute("products", productRepository.findAll());
        return "admin";
    }

    @GetMapping("/admin/list")
    protected String editProduct(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin_list";
    }

    @GetMapping("/admin/add_product")
    protected String addProduct(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("newProduct", new Product("name", "description", BigDecimal.ZERO, categoryRepository.findByName("category")));
        return "admin_add_product";
    }

    @PostMapping("/admin/add_product/{name}")
    public String addProductConfirm(@ModelAttribute Product newProduct, @PathVariable("name") String name, Model model) {
            model.addAttribute("newProduct", newProduct);
            model.addAttribute("products", productRepository.findAll());

            if (productRepository.findByName(newProduct.getName()) == null) {
                newProduct = new Product(newProduct.getName(), newProduct.getDescription(), newProduct.getPrice(), newProduct.getCategory());
                productRepository.save(newProduct);
                return "admin_product_added";
            } else {
                return "admin_product_not_added";
            }
    }

    @GetMapping("/admin/categories")
    protected String editCategory(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin_categories";
    }

    @GetMapping("/admin/category/{name}/edit")
    protected String editCategory(@PathVariable("name") String name, Model model) {
        model.addAttribute("category", categoryRepository.findByName(name));
        model.addAttribute("editCategory", new Category(name));
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("productsByCategory", productRepository.findByCategory(categoryRepository.findByName(name)));
        return "admin_category_edit";
    }

    @PostMapping("/admin/category/{name}/update")
    public String confirmChange(@ModelAttribute Category editCategory, @PathVariable("name") String name, Model model) {
            model.addAttribute("editCategory", editCategory);
            model.addAttribute("categories", categoryRepository.findAll());
//        categoryRepository.replaceCategory(categoryRepository.findByName(name),editCategory);
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

        if (categoryRepository.findByName(newCategory.getName()) == null) {
            newCategory = new Category(newCategory.getName());
            categoryRepository.save(newCategory);
            return "admin_category_added";
        } else {
            return "admin_category_not_added";
        }
    }

}
