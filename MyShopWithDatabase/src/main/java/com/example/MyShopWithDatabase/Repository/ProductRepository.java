package com.example.MyShopWithDatabase.Repository;

import com.example.MyShopWithDatabase.model.Category;
import com.example.MyShopWithDatabase.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    Product findByName(String name);
}
