package com.example.MyShopWithDatabase.web;

import com.example.MyShopWithDatabase.DAO.CartDAO;
import com.example.MyShopWithDatabase.DAO.CategoryDAO;
import com.example.MyShopWithDatabase.DAO.ProductDAO;
import com.example.MyShopWithDatabase.model.Item;
import com.example.MyShopWithDatabase.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    private CartDAO cartDAO;
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    Product replace;

    public ProductController(CartDAO cartDAO, ProductDAO productDAO, CategoryDAO categoryDAO) {
        this.cartDAO = cartDAO;
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
    }

    @GetMapping("/products/list")
    protected String showAllProducts(Model model) {
        model.addAttribute("products", productDAO.all());
        model.addAttribute("categories", categoryDAO.getAllCategories());
        return "products_list";
    }

    @GetMapping("/products/{category}")
    protected String showProductsByCategory(@PathVariable("category") String category, Model model) {
        model.addAttribute("products", productDAO.byCategory(category));
        model.addAttribute("categories", categoryDAO.getAllCategories());
        return "products_list";
    }

    @GetMapping("/products/list/{name}")
    protected String showProduct(@PathVariable("name") String name, Model model) {
        model.addAttribute("product", productDAO.byName(name));
        model.addAttribute("addedItem", new Item(productDAO.byName(name), 0));
        model.addAttribute("categories", categoryDAO.getAllCategories());
        return "product_details";
    }

    @GetMapping("/products/list/{name}/edit")
    protected String editProduct(@PathVariable("name") String name, Model model) {
        model.addAttribute("product", productDAO.byName(name));
        model.addAttribute("editProduct", new Product(productDAO.byName(name).getName(), productDAO.byName(name).getDescription(), productDAO.byName(name).getPrice(), productDAO.byName(name).getCategory()));
        model.addAttribute("categories", categoryDAO.getAllCategories());
        return "product_edit";
    }

    @PostMapping("/products/list/{name}/update")
    public String confirmChange(@ModelAttribute Product editProduct, @PathVariable("name") String name, Model model) {
        if(checkNotEmpty(editProduct)) {
            model.addAttribute("editProduct", editProduct);
            model.addAttribute("product", productDAO.byName(name));
            model.addAttribute("categories", categoryDAO.getAllCategories());
            replace = new Product(editProduct.getName(), editProduct.getDescription(), editProduct.getPrice(), editProduct.getCategory());
            return "update_success";
        } else
            return "redirect:sorry";
    }

    @PostMapping("/products/list/{name}/update/confirmed")
    public String inputChange(@PathVariable("name") String name, Model model) {
        productDAO.replaceProduct(productDAO.byName(name),replace);
        model.addAttribute("products", productDAO.all());
        model.addAttribute("categories", categoryDAO.getAllCategories());
        return "products_list";
    }

    @PostMapping("/products/list/{name}/addedToCart")
    public String addedToCart(@ModelAttribute Item addedItem, @PathVariable("name") String name, Model model) {
            if (cartDAO.getItemByProductName(name) == null) {
                addedItem.setProduct(productDAO.byName(name));
                cartDAO.addItem(addedItem);
            } else {
                if (addedItem.getQuantity() == 0) {
                    cartDAO.removeItem(cartDAO.getItemByProductName(name));
                } else {
                    cartDAO.getItemByProductName(name).setQuantity(addedItem.getQuantity());
                }
            }
            model.addAttribute("addedItem", addedItem);
            model.addAttribute("cartDAO", cartDAO);
            return "redirect:/cart";//tu była zmiana z cart na redirect
    }

    @GetMapping("/cart")
    protected String showCart( Model model) {
        model.addAttribute("cartDAO", cartDAO);
        return "cart";
    }
    @GetMapping("/sorry")
    public String error() {
        return "errorMessage";
    }

    public static boolean checkNotEmpty(Product product) {
        return (product.getName()!=null && product.getName().length()>0)
                && (product.getDescription()!=null && product.getDescription().length()>0);
    }

}
