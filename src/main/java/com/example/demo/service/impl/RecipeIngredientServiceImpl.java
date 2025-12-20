package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.RecipeIngredientService;
import org.springframework.stereotype.Service;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository repo;
    private final MenuItemRepository menuRepo;
    private final IngredientRepository ingredientRepo;

    public RecipeIngredientServiceImpl(
            RecipeIngredientRepository repo,
            MenuItemRepository menuRepo,
            IngredientRepository ingredientRepo) {
        this.repo = repo;
        this.menuRepo = menuRepo;
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public RecipeIngredient add(RecipeIngredient recipeIngredient) {

        // 🔑 Extract IDs from incoming JSON
        Long menuItemId = recipeIngredient.getMenuItem().getId();
        Long ingredientId = recipeIngredient.getIngredient().getId();

        // 🔑 Re-fetch managed entities from DB
        MenuItem menuItem = menuRepo.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found"));

        Ingredient ingredient = ingredientRepo.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));

        // 🔑 Attach managed entities
        recipeIngredient.setMenuItem(menuItem);
        recipeIngredient.setIngredient(ingredient);

        // 🔑 Save safely
        return repo.save(recipeIngredient);
    }
}
