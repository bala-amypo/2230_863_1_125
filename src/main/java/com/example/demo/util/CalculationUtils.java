package com.example.demo.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationUtils {
    
    public static BigDecimal calculateProfitMargin(BigDecimal sellingPrice, BigDecimal totalCost) {
        if (sellingPrice == null || totalCost == null) {
            return BigDecimal.ZERO;
        }
        return sellingPrice.subtract(totalCost).setScale(2, RoundingMode.HALF_UP);
    }
    
    public static Double calculateProfitPercentage(BigDecimal sellingPrice, BigDecimal totalCost) {
        if (sellingPrice == null || totalCost == null || sellingPrice.compareTo(BigDecimal.ZERO) == 0) {
            return 0.0;
        }
        BigDecimal profit = sellingPrice.subtract(totalCost);
        return profit.divide(sellingPrice, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();
    }
}