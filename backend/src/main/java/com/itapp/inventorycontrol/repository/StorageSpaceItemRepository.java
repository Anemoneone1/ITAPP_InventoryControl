package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.StorageSpaceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageSpaceItemRepository extends JpaRepository<StorageSpaceItem, Long> {
    List<StorageSpaceItem> findAllByItemCompanyId(Long id);
}
