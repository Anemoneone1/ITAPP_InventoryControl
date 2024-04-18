package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.StorageSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageSpaceRepository extends JpaRepository<StorageSpace, Long> {
    List<StorageSpace> findAllByWarehouseCompanyId(Long id);
}
