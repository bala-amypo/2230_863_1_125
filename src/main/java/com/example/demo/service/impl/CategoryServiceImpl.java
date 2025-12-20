package com.example.demo.service.impl;

import com.example.demo.entity.Category;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    public CategoryServiceImpl(CategoryRepository repo) {
        this.repo = repo;
    }

    public Category create(Category category) {
        return repo.save(category);
    }

    public Category update(Long id, Category category) {
        Category existing = get(id);
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return repo.save(existing);
    }

    public Category get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found"));
    }

    public List<Category> getAll() {
        return repo.findAll();
    }

    public void deactivate(Long id) {
        Category category = get(id);
        category.setActive(false);
        repo.save(category);
    }
}
