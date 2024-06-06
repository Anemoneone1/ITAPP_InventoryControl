package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    List<Storage> findAllByWarehouseCompanyId(Long id);

    List<Storage> findAllByWarehouseCompanyIdAndWarehouseId(Long id, Long warehouseId);
}
