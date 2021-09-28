package com.example.MyShopWithDatabase;

import com.example.MyShopWithDatabase.Repository.*;
import com.example.MyShopWithDatabase.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class MyShopWithDatabaseApplication implements CommandLineRunner {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	OrdersRepository ordersRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyShopWithDatabaseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("---------------------------------PRINT Orders---------------------------------");
		List<Orders> orders = ordersRepository.findAll();
		for (Orders order : orders) {
			System.out.println(order);
		}

		System.out.println("---------------------------------ADD Categories---------------------------------");
		categoryRepository.save(new Category(
				"OWOCE"));
		categoryRepository.save(new Category(
				"WARZYWA"));
		categoryRepository.save(new Category(
				"ORZECHY"));

		System.out.println("---------------------------------ADD Products---------------------------------");
		productRepository.save(new Product(
					"Jabłko",
					"bardzo słodkie, czerwone lub zielone",
					new BigDecimal("25.00"),
					categoryRepository.getById(1L)));
		productRepository.save(new Product(
				"Ziemniak",
				"Jabłko wśród warzyw",
				new BigDecimal("99.99"),
				categoryRepository.getById(2L)));
		productRepository.save(new Product(
				"Orzech włoski",
				"Dobry na pamięć",
				new BigDecimal("29.99"),
				categoryRepository.getById(3L)));
		productRepository.save(new Product(
				"Ogórek",
				"zielony, gruntowy",
				new BigDecimal("5.50"),
				categoryRepository.getById(2L)));
		productRepository.save(new Product(
				"Kalarepa",
				"dobra do pochrupania",
				new BigDecimal("25.00"),
				categoryRepository.getById(2L)));

		System.out.println("---------------------------------CREATE Customers---------------------------------");
		Customer customer1 = new Customer("Adam", "Małysz");
		Customer customer2 = new Customer("Andrzej", "Gołota");
		Customer customer3 = new Customer("Edyta", "Górniak");

		customerRepository.save(customer1);
		customerRepository.save(customer2);
		customerRepository.save(customer3);

		System.out.println("---------------------------------CREATE Orders---------------------------------");
		Orders order1 = new Orders(customer1);
		Orders order2 = new Orders(customer2);
		Orders order3 = new Orders(customer3);

		ordersRepository.save(order1);
		ordersRepository.save(order2);
		ordersRepository.save(order3);

		System.out.println("---------------------------------CREATE Items---------------------------------");
		Item item1 = new Item(productRepository.getById(4L), 7, order1);
		Item item2 = new Item(productRepository.getById(5L), 2, order1);
		Item item3 = new Item(productRepository.getById(6L), 4, order2);
		Item item4 = new Item(productRepository.getById(7L), 11, order2);
		Item item5 = new Item(productRepository.getById(8L), 12, order3);

		itemRepository.save(item1);
		itemRepository.save(item2);
		itemRepository.save(item3);
		itemRepository.save(item4);
		itemRepository.save(item5);

		System.out.println("---------------------------------PRINT CATEGORIES---------------------------------");
		List<Category> categories = categoryRepository.findAll();
		categories.forEach(System.out::println);

		System.out.println("---------------------------------PRINT PRODUCTS---------------------------------");
		List<Product> products = productRepository.findAll();
		products.forEach(System.out::println);

		System.out.println("---------------------------------PRINT Customers---------------------------------");
		List<Customer> customers = customerRepository.findAll();
		customers.forEach(System.out::println);

		System.out.println("---------------------------------PRINT Items---------------------------------");
		List<Item> items = itemRepository.findAll();
		items.forEach(System.out::println);

		System.out.println("---------------------------------PRINT Orders---------------------------------");
		List<Orders> orders2 = ordersRepository.findAll();
		for (Orders order : orders2) {
			System.out.println(order);
		}

	}
}
