package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.entity.Compliance;
import com.itapp.inventorycontrol.entity.ComplianceAgreement;
import com.itapp.inventorycontrol.entity.ItemCompliance;
import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.exception.ICErrorType;
import com.itapp.inventorycontrol.exception.ICException;
import com.itapp.inventorycontrol.repository.ComplianceAgreementRepository;
import com.itapp.inventorycontrol.security.SignedInUsernameGetter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class ComplianceAgreementService {
    private final SignedInUsernameGetter signedInUsernameGetter;
    private final ComplianceAgreementRepository complianceAgreementRepository;
    private final ComplianceService complianceService;

    public ComplianceAgreement getOrThrow(Long id) {
        return complianceAgreementRepository.findById(id).orElseThrow(() -> new ICException(ICErrorType.IC_702));
    }

    public List<ComplianceAgreement> getAll() {
        User user = signedInUsernameGetter.getUser();
        return complianceAgreementRepository.findAllByComplianceCompanyId(user.getCompany().getId());
    }

    public ComplianceAgreement create(ComplianceAgreement complianceAgreement) {
        User user = signedInUsernameGetter.getUser();
        Compliance compliance = complianceService.getOrThrow(complianceAgreement.getCompliance().getId());
        complianceService.validateUserOwnsCompliance(user, compliance);

        validateEndIsAfterStart(complianceAgreement.getStart(), complianceAgreement.getEnd());
        validateNoOverlappingAgreements(complianceAgreement);

        return complianceAgreementRepository.save(complianceAgreement);
    }

    public void delete(Long complianceAgreementId) {
        User user = signedInUsernameGetter.getUser();
        ComplianceAgreement complianceAgreement = getOrThrow(complianceAgreementId);

        Compliance compliance = complianceService.getOrThrow(complianceAgreement.getCompliance().getId());
        validateUserOwnsCompliance(user, compliance);

        complianceAgreementRepository.deleteById(complianceAgreementId);
    }

    public void validateUserOwnsCompliance(User user, Compliance compliance) {
        if (compliance.getCompany().getId() != user.getCompany().getId()) {
            throw new ICException(ICErrorType.IC_702);
        }
    }

    private void validateEndIsAfterStart(Date start, Date end) {
        if (!end.after(start)) {
            throw new ICException(ICErrorType.IC_703);
        }
    }

    private void validateNoOverlappingAgreements(ComplianceAgreement agreement) {
        List<ComplianceAgreement> agreements = complianceAgreementRepository.findAllByComplianceIdAndEndAfter(
                agreement.getCompliance().getId(), agreement.getStart());
        if (!agreements.isEmpty()) {
            throw new ICException(ICErrorType.IC_704);
        }
    }

    public void validateComplianceAgreementsActive(Set<ItemCompliance> compliances) {
        if (compliances.isEmpty()) return;

        List<ComplianceAgreement> complianceList = complianceAgreementRepository.findAllByComplianceIdInAndDateBetween(
                compliances.stream().map(ic -> ic.getCompliance().getId()).toList(),
                new Date());
        if (complianceList.size() != compliances.size()) {
            throw new ICException(ICErrorType.IC_705);
        }
    }
}
