package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.ItemCompliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemComplianceRepository extends JpaRepository<ItemCompliance, Long> {
    void deleteAllByItemId(Long itemId);
}
