package com.example.MyShopWithDatabase.Repository;

import com.example.MyShopWithDatabase.model.Customer;
import com.example.MyShopWithDatabase.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
