package com.example.demo.service.impl;

import com.example.demo.entity.Ingredient;
import com.example.demo.exception.*;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.service.IngredientService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository repo;

    public IngredientServiceImpl(IngredientRepository repo) {
        this.repo = repo;
    }

    public Ingredient create(Ingredient ingredient) {
        if (repo.existsByName(ingredient.getName())) {
            throw new BadRequestException("Email already in use");
        }
        if (ingredient.getCostPerUnit().doubleValue() <= 0) {
            throw new BadRequestException("Cost per unit");
        }
        return repo.save(ingredient);
    }

    public Ingredient update(Long id, Ingredient ingredient) {
        Ingredient existing = get(id);
        existing.setName(ingredient.getName());
        existing.setUnit(ingredient.getUnit());
        existing.setCostPerUnit(ingredient.getCostPerUnit());
        return repo.save(existing);
    }

    public Ingredient get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found"));
    }

    public List<Ingredient> getAll() {
        return repo.findAll();
    }

    public void deactivate(Long id) {
        Ingredient ingredient = get(id);
        ingredient.setActive(false);
        repo.save(ingredient);
    }
}
