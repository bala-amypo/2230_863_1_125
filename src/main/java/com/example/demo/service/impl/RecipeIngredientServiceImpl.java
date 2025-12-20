package com.example.demo.service.impl;

import com.example.demo.entity.RecipeIngredient;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RecipeIngredientRepository;
import com.example.demo.service.RecipeIngredientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository repo;

    public RecipeIngredientServiceImpl(RecipeIngredientRepository repo) {
        this.repo = repo;
    }

    public RecipeIngredient add(RecipeIngredient recipeIngredient) {
        return repo.save(recipeIngredient);
    }

    public RecipeIngredient update(Long id, Double quantity) {
        RecipeIngredient ri = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found"));
        ri.setQuantityRequired(quantity);
        return repo.save(ri);
    }

    public List<RecipeIngredient> getByMenuItem(Long menuItemId) {
        return repo.findByMenuItemId(menuItemId);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
