package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.StorageConditionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageConditionItemRepository extends JpaRepository<StorageConditionItem, Long> {
}
