package com.example.MyShopWithDatabase.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id_category")
    private Long id;
    @Column(name = "category_name")
    String name;
    @OneToMany(mappedBy="category")
    @Column(name = "products")
    private List<Product> products = new ArrayList<>();

    public Category() {

    }
    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
