package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "profit_records")
public class ProfitCalculationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MenuItem menuItem;

    private BigDecimal totalCost;
    private BigDecimal profitMargin;

    private Timestamp calculatedAt;

    @PrePersist
    public void onCreate() {
        calculatedAt = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MenuItem getMenuItem() { return menuItem; }
    public void
