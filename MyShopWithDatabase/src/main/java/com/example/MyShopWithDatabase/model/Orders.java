package com.example.MyShopWithDatabase.model;

import com.example.MyShopWithDatabase.Repository.OrdersRepository;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id_orders")
    private Long id;
    @Column(name = "date")
    LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @OneToMany(mappedBy="orders", fetch = FetchType.EAGER)
    @Column(name = "items")
    private List<Item> items = new ArrayList<>();

    public Orders() {
    }
    public Orders(Customer customer) {
        this.customer = customer;
    }

    public String printOrderContent(){
        StringBuilder sb = new StringBuilder();
            for (Item item : items) {
                sb.append("[");
                sb.append(item.getId());
                sb.append("]");
            }
    return sb.toString();
    };

    @Override
    public String toString() {

        return "Orders{" +
                "id=" + id +
                ", date=" + date +
                ", customer=" + customer.getId() +
                ", items=" + printOrderContent() +
                '}';
    }
}
