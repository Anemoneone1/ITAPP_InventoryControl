package com.itapp.inventorycontrol.repository;

import com.itapp.inventorycontrol.entity.ComplianceAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComplianceAgreementRepository extends JpaRepository<ComplianceAgreement, Long> {
    List<ComplianceAgreement> findAllByComplianceCompanyId(Long id);

    @Query("SELECT ca FROM ComplianceAgreement ca WHERE ca.compliance.id = :complianceId AND ca.end > :currentDate")
    List<ComplianceAgreement> findAllByComplianceIdAndEndAfter(Long complianceId, LocalDate currentDate);

    @Query("SELECT ca FROM ComplianceAgreement ca WHERE ca.compliance.id IN :complianceIds AND ca.start < :currentDate AND ca.end > :currentDate")
    List<ComplianceAgreement> findAllByComplianceIdInAndDateBetween(List<Long> complianceIds, LocalDate currentDate);
}
