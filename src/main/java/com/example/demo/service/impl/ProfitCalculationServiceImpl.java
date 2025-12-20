package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.ProfitCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProfitCalculationServiceImpl
        implements ProfitCalculationService {

    private final MenuItemRepository menuItemRepo;
    private final RecipeIngredientRepository recipeRepo;
    private final ProfitCalculationRecordRepository profitRepo;

    public ProfitCalculationServiceImpl(
            MenuItemRepository menuItemRepo,
            RecipeIngredientRepository recipeRepo,
            ProfitCalculationRecordRepository profitRepo) {
        this.menuItemRepo = menuItemRepo;
        this.recipeRepo = recipeRepo;
        this.profitRepo = profitRepo;
    }

    public ProfitCalculationRecord calculate(Long menuItemId) {

        MenuItem menuItem = menuItemRepo.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("not found"));

        List<RecipeIngredient> ingredients =
                recipeRepo.findByMenuItemId(menuItemId);

        BigDecimal totalCost = BigDecimal.ZERO;

        for (RecipeIngredient ri : ingredients) {
            BigDecimal costPerUnit =
                    ri.getIngredient().getCostPerUnit();
            BigDecimal qty =
                    BigDecimal.valueOf(ri.getQuantityRequired());

            totalCost = totalCost.add(costPerUnit.multiply(qty));
        }

        BigDecimal profit =
                menuItem.getSellingPrice().subtract(totalCost);

        ProfitCalculationRecord record =
                new ProfitCalculationRecord();

        record.setMenuItem(menuItem);
        record.setTotalCost(totalCost);
        record.setProfitMargin(profit);

        return profitRepo.save(record);
    }

    public ProfitCalculationRecord get(Long id) {
        return profitRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found"));
    }

    public List<ProfitCalculationRecord> getByMenuItem(Long menuItemId) {
        return profitRepo.findByMenuItemId(menuItemId);
    }

    public List<ProfitCalculationRecord> getAll() {
        return profitRepo.findAll();
    }
}
