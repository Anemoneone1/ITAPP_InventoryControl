package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.StorageConditionStorageSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageConditionStorageSpaceRepository extends JpaRepository<StorageConditionStorageSpace, Long> {
    void removeAllByStorageSpaceId(Long id);
}
