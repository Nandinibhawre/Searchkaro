package com.Spring.elitedemo1.Controller;

import com.Spring.elitedemo1.Model.Categries;
import com.Spring.elitedemo1.Services.CategriesServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CategoriesController {

    @Autowired
    private final CategriesServices service;

    public CategoriesController(CategriesServices service) {
        this.service = service;
    }

    // GET categories for logged-in user
    @GetMapping("/categories")
    public List<Categries> getCategories(HttpServletRequest request) {

        String userId = (String) request.getAttribute("userId");
        return service.getCategoriesByUserId(userId);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Categries> getCategoryById(
            @PathVariable String categoryId,
            HttpServletRequest request) {

        String userId = (String) request.getAttribute("userId");

        Optional<Categries> category =
                service.getCategoryById(categoryId, userId);

        return category
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE category
    @PostMapping("/postCategories")
    public Categries createCategory(@RequestBody Categries category,
                                   HttpServletRequest request) {

        String userId = (String) request.getAttribute("userId");
        category.setUserId(userId);

        return service.saveCategory(category);
    }

    // ✅ DELETE category by ID
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable String categoryId,
            HttpServletRequest request) {

        String userId = (String) request.getAttribute("userId");

        boolean deleted = service.deleteCategory(categoryId, userId);

        if (deleted) {
            return ResponseEntity.ok("Category deleted successfully");
        } else {
            return ResponseEntity.status(404)
                    .body("Category not found or unauthorized");
        }
    }
}
