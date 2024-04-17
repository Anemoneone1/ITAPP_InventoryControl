package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    List<Warehouse> findAllByCompanyId(Long id);
}
