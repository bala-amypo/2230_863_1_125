package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.RecipeIngredientService;
import org.springframework.stereotype.Service;

import java.util.List;

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

        Long menuItemId = recipeIngredient.getMenuItem().getId();
        Long ingredientId = recipeIngredient.getIngredient().getId();

        MenuItem menuItem = menuRepo.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found"));

        Ingredient ingredient = ingredientRepo.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));

        recipeIngredient.setMenuItem(menuItem);
        recipeIngredient.setIngredient(ingredient);

        return repo.save(recipeIngredient);
    }

    @Override
    public RecipeIngredient update(Long id, Double quantity) {
        RecipeIngredient ri = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found"));
        ri.setQuantityRequired(quantity);
        return repo.save(ri);
    }

    @Override
    public List<RecipeIngredient> getByMenuItem(Long menuItemId) {
        return repo.findByMenuItemId(menuItemId);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("not found");
        }
        repo.deleteById(id);
    }
}
