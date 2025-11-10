package com.akimi.modules.forest_inventory.inventory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akimi.modules.forest_inventory.forest.Forest;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByForest(Forest forest);
}
