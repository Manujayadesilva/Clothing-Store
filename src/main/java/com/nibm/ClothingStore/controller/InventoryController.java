package com.nibm.ClothingStore.controller;

import com.nibm.ClothingStore.model.Inventory;
import com.nibm.ClothingStore.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:3000")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping
    public List<Inventory> getAllItems() {
        return inventoryRepository.findAll();
    }

    @PostMapping
    public Inventory createItem(@RequestBody Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateItem(@PathVariable Long id, @RequestBody Inventory itemDetails) {
        Inventory item = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        item.setItemName(itemDetails.getItemName());
        item.setQuantity(itemDetails.getQuantity());
        item.setPrice(itemDetails.getPrice());

        Inventory updatedItem = inventoryRepository.save(item);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteItem(@PathVariable Long id) {
        Inventory item = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        inventoryRepository.delete(item);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}