package com.example.demo.service;

import com.example.demo.entity.ProfitCalculationRecord;
import java.util.List;

public interface ProfitCalculationService {

    ProfitCalculationRecord calculate(Long menuItemId);
    ProfitCalculationRecord get(Long id);
    List<ProfitCalculationRecord> getByMenuItem(Long menuItemId);
    List<ProfitCalculationRecord> getAll();
}
