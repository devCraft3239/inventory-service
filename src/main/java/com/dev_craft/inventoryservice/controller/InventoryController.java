package com.dev_craft.inventoryservice.controller;

import com.dev_craft.inventoryservice.entity.InventoryItem;
import com.dev_craft.inventoryservice.repository.InventoryItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class InventoryController {
    private final InventoryItemRepository inventoryItemRepository;
 
    @Autowired
    public InventoryController(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @GetMapping("/api/inventory")
    public ResponseEntity<List<InventoryItem>> findInventory() {
        log.info("Finding inventory for products");
        List<InventoryItem> inventoryItems = inventoryItemRepository.findAll();
        return ResponseEntity.ok(inventoryItems);
    }

    @GetMapping("/api/inventory/{productCode}")
    public ResponseEntity<InventoryItem> findInventoryByProductCode(@PathVariable("productCode") String productCode) {
        log.info("Finding inventory for product code :"+productCode);
        Optional<InventoryItem> inventoryItem = inventoryItemRepository.findByProductCode(productCode);
        return inventoryItem.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}