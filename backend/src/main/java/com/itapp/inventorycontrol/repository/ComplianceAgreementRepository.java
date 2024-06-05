package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.ComplianceAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceAgreementRepository extends JpaRepository<ComplianceAgreement, Long> {
    List<ComplianceAgreement> findAllByComplianceCompanyId(Long id);
}
