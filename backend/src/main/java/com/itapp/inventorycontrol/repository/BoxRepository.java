package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {
    Optional<Box> findByUuid(String uuid);

    List<Box> findAllByStorageWarehouseCompanyId(Long id);

    @Query("SELECT b FROM Box b " +
            "WHERE b.storage.warehouse.id = :warehouseId " +
            "AND (b.expirationDate < :currentDate " +
            "OR DATEDIFF(b.expirationDate, b.creationDate) * 0.2 > DATEDIFF(b.expirationDate, :currentDate))")
    List<Box> findAllBoxesWithWarningsByWarehouse(Long warehouseId, LocalDate currentDate);
}
