package com.example.MyShopWithDatabase.Repository;

import com.example.MyShopWithDatabase.model.Item;
import com.example.MyShopWithDatabase.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {


}
