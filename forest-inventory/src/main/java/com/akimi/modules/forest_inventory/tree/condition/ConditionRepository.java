package com.akimi.modules.forest_inventory.tree.condition;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionRepository extends JpaRepository<Condition, Long> {
    Collection<Condition> findByNameLikeIgnoreCase(String name);
}
