package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {
    Optional<Box> findByUuid(String uuid);

    List<Box> findAllByStorageWarehouseCompanyId(Long id);
}
