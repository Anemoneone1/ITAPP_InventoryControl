package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.StorageConditionStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageConditionStorageRepository extends JpaRepository<StorageConditionStorage, Long> {
    void deleteAllByStorageId(Long storageId);
}
