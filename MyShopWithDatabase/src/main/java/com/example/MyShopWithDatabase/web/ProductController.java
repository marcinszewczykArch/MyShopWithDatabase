package com.example.MyShopWithDatabase.web;

import com.example.MyShopWithDatabase.DAO.CartDAO;
import com.example.MyShopWithDatabase.Repository.CategoryRepository;
import com.example.MyShopWithDatabase.Repository.ItemRepository;
import com.example.MyShopWithDatabase.Repository.OrdersRepository;
import com.example.MyShopWithDatabase.Repository.ProductRepository;
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

//    private CartDAO cartDAO;
    private OrdersRepository ordersRepository;
//    private ProductDAO productDAO;
    private ProductRepository productRepository;
//    private CategoryDAO categoryDAO;
    private CategoryRepository categoryRepository;
    private ItemRepository itemRepository;
    
    Product replace;

    public ProductController(OrdersRepository ordersRepository, ProductRepository productRepository, CategoryRepository categoryRepository, ItemRepository itemRepository) {
        this.ordersRepository = ordersRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/products/list")
    protected String showAllProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "products_list";
    }

    @GetMapping("/products/{category}")
    protected String showProductsByCategory(@PathVariable("category") String category, Model model) {
        model.addAttribute("products", productRepository.findByCategory(categoryRepository.findByName(category)));
        model.addAttribute("categories", categoryRepository.findAll());
        return "products_list";
    }

    @GetMapping("/products/list/{name}")
    protected String showProduct(@PathVariable("name") String name, Model model) {
        model.addAttribute("product", productRepository.findByName(name));
        model.addAttribute("addedItem", new Item(productRepository.findByName(name), 0));
        model.addAttribute("categories", categoryRepository.findAll());
        return "product_details";
    }

    @GetMapping("/products/list/{name}/edit")
    protected String editProduct(@PathVariable("name") String name, Model model) {
        model.addAttribute("product", productRepository.findByName(name));
        model.addAttribute("editProduct", new Product(productRepository.findByName(name).getName(), productRepository.findByName(name).getDescription(), productRepository.findByName(name).getPrice(), productRepository.findByName(name).getCategory()));
        model.addAttribute("categories", categoryRepository.findAll());
        return "product_edit";
    }

    @PostMapping("/products/list/{name}/update")
    public String confirmChange(@ModelAttribute Product editProduct, @PathVariable("name") String name, Model model) {
        if(checkNotEmpty(editProduct)) {
            model.addAttribute("editProduct", editProduct);
            model.addAttribute("product", productRepository.findByName(name));
            model.addAttribute("categories", categoryRepository.findAll());
            replace = new Product(editProduct.getName(), editProduct.getDescription(), editProduct.getPrice(), editProduct.getCategory());
            return "update_success";
        } else
            return "redirect:sorry";
    }

    @PostMapping("/products/list/{name}/update/confirmed")
    public String inputChange(@PathVariable("name") String name, Model model) {
//        productRepository.replaceProduct(productRepository.findByName(name),replace);
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "products_list";
    }

    @PostMapping("/products/list/{name}/addedToCart")
    public String addedToCart(@ModelAttribute Item addedItem, @PathVariable("name") String name, Model model) {
        if (itemRepository.findByProduct(productRepository.findByName(name)) == null) {
                addedItem.setProduct(productRepository.findByName(name)); //powinno juz być ustawione
                addedItem.setQuantity(addedItem.getQuantity());//powinno juz być ustawione
                addedItem.setOrders(ordersRepository.findAll().get(0));
                itemRepository.save(addedItem);
            } else {
                if (addedItem.getQuantity() == 0) {
                    itemRepository.delete(
                        itemRepository.findByProduct(productRepository.findByName(name))
                    );
                } else {
                    itemRepository.findByProduct(productRepository.findByName(name)).setQuantity(addedItem.getQuantity());
                    itemRepository.save(itemRepository.findByProduct(productRepository.findByName(name)));
                }
            }
            model.addAttribute("addedItem", addedItem);
            model.addAttribute("cartDAO", itemRepository.findAll());
            return "redirect:/cart";//tu była zmiana z cart na redirect
    }

    @GetMapping("/cart")
    protected String showCart( Model model) {
        model.addAttribute("cartDAO", itemRepository.findAll());
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
