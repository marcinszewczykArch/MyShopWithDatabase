package com.example.MyShopWithDatabase.DAO;

import com.example.MyShopWithDatabase.model.Category;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.LinkedList;

@Repository
public class CategoryDAO {

    private LinkedList<Category> categories = new LinkedList<Category>(Arrays.asList(
            new Category("OWOCE"),
            new Category("WARZYWA"),
            new Category("ORZECHY")));

    public LinkedList<Category> getAllCategories() {
        return categories;
    }

    public Category getCategoryByName(String name) {
        for (Category category : categories) {
            if (name.equals(category.getName())) {
                return category;
            }
        }
        return null;
    }

    public Category byName(String name) {
        for (Category category : categories) {
            if (name.equals(category.getName())) {
                return category;
            }
        }
        return null;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void removeCategory(String categoryString) {
        categories.remove(new Category(categoryString));
    }

    public void replaceCategory(Category oldCategory, Category newCategory) {
        oldCategory.setName(newCategory.getName());
    }
}
