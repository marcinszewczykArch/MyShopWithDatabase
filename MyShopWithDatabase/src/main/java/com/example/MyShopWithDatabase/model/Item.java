package com.example.MyShopWithDatabase.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id_item")
    private Long id;
    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "orders")
    private Orders orders;

    public Item() {
    }
    public Item(Product product, int quantity) {
        this.quantity = quantity;
        this.product = product;
    }
    public Item(Product product, int quantity, Orders orders) {
        this.quantity = quantity;
        this.product = product;
        this.orders = orders;
    }

    public BigDecimal getAmount() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", order=" + orders.getId() +
                ", quantity=" + quantity +
                ", product=" + product.getName() +
                '}';
    }
}
