package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.Compliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ComplianceRepository extends JpaRepository<Compliance, Long> {
    List<Compliance> findAllByCompanyId(Long id);

    Set<Compliance> findAllByCompanyIdAndIdIn(Long companyId, List<Long> complianceIds);
}
