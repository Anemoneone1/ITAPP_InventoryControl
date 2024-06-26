package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.StorageCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StorageConditionRepository extends JpaRepository<StorageCondition, Long> {
    List<StorageCondition> findAllByCompanyId(Long id);

    Set<StorageCondition> findAllByCompanyIdAndIdIn(Long companyId, List<Long> storageConditionIds);
}
