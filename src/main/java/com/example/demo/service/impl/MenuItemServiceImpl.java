package com.example.demo.service.impl;

import com.example.demo.entity.MenuItem;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.MenuItemRepository;
import com.example.demo.service.MenuItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository repo;

    public MenuItemServiceImpl(MenuItemRepository repo) {
        this.repo = repo;
    }

    public MenuItem create(MenuItem menuItem) {
        return repo.save(menuItem);
    }

    public MenuItem update(Long id, MenuItem menuItem) {
        MenuItem existing = get(id);
        existing.setName(menuItem.getName());
        existing.setDescription(menuItem.getDescription());
        existing.setSellingPrice(menuItem.getSellingPrice());
        return repo.save(existing);
    }

    public MenuItem get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found"));
    }

    public List<MenuItem> getAll() {
        return repo.findAll();
    }

    public void deactivate(Long id) {
        MenuItem item = get(id);
        item.setActive(false);
        repo.save(item);
    }
}
