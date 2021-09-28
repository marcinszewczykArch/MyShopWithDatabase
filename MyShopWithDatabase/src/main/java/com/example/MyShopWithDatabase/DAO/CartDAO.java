package com.example.MyShopWithDatabase.DAO;

import com.example.MyShopWithDatabase.model.Item;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


@Repository
public class CartDAO {
    private final BigDecimal DISCOUNT_COEFFICIENT = new BigDecimal("0.9");

    private List<Item> items = new LinkedList<>();

    public List<Item> getAllItems() {
        return items;
    }

    public Item getItemByProductName(String name) {
        if(items != null) {
            for (Item item : items) {
                if (name.equals(item.getProduct().getName())) {
                    return item;
                }
            }
        }
        return null;
    }

    public BigDecimal getTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        if(items != null) {
            for (Item item : items) {
                sum = sum.add(item.getAmount());
            }
        }
        return sum;
    }

    public BigDecimal getDiscountFor10ProductsOrMore() {
        int totalQuantity = getTotalQuantity();
        if (totalQuantity>=10) {
            return getTotal().multiply(DISCOUNT_COEFFICIENT);
        } else {
            return getTotal();
        }
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;

        for (Item item : getAllItems()) {
            totalQuantity = totalQuantity + item.getQuantity();
        }
        return totalQuantity;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

}
