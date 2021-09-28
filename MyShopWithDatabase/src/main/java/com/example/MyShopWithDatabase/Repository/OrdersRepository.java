package com.example.MyShopWithDatabase.Repository;

import com.example.MyShopWithDatabase.model.Item;
import com.example.MyShopWithDatabase.model.Orders;
import com.example.MyShopWithDatabase.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

     default void addItemsToOrder(Orders orders, Item... items) {
        if(orders != null) {
            for(Item item: items) {
                orders.getItems().add(item);
            }
        }
     }
}
