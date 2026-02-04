package com.Spring.elitedemo1.Services;

import com.Spring.elitedemo1.Model.Categries;
import com.Spring.elitedemo1.Repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategriesServices {

    @Autowired
    private final CategoryRepo repository;

    public CategriesServices(CategoryRepo repository) {
        this.repository = repository;
    }

    // 🔹 Get all categories for a user
    public List<Categries> getCategoriesByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    // 🔹 Save new category
    public Categries saveCategory(Categries category) {
        return repository.save(category);
    }

    // 🔹 Get category by ID + userId (SECURE)
    public Optional<Categries> getCategoryById(String categoryId, String userId) {
        return repository.findByCategoryIdAndUserId(categoryId, userId);
    }

    // 🔹 Delete category by ID + userId
    public boolean deleteCategory(String categoryId, String userId) {
        Optional<Categries> category =
                repository.findByCategoryIdAndUserId(categoryId, userId);

        if (category.isPresent()) {
            repository.delete(category.get());
            return true;
        }
        return false;
    }
}
