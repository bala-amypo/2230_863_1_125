package com.example.demo.service;

import com.example.demo.entity.MenuItem;
import java.util.List;

public interface MenuItemService {
    MenuItem create(MenuItem menuItem);
    MenuItem update(Long id, MenuItem menuItem);
    MenuItem get(Long id);
    List<MenuItem> getAll();
    void deactivate(Long id);
}
