package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.entity.Compliance;
import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.exception.ICErrorType;
import com.itapp.inventorycontrol.exception.ICException;
import com.itapp.inventorycontrol.repository.ComplianceRepository;
import com.itapp.inventorycontrol.security.SignedInUsernameGetter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ComplianceService {
    private final SignedInUsernameGetter signedInUsernameGetter;
    private final ComplianceRepository complianceRepository;

    public Compliance getOrThrow(Long id) {
        return complianceRepository.findById(id).orElseThrow(() -> new ICException(ICErrorType.IC_701));
    }

    public List<Compliance> getAll() {
        User user = signedInUsernameGetter.getUser();
        return complianceRepository.findAllByCompanyId(user.getCompany().getId());
    }

    public Compliance create(Compliance compliance) {
        User user = signedInUsernameGetter.getUser();
        compliance.setCompany(user.getCompany());
        return complianceRepository.save(compliance);
    }

    public void delete(Long complianceId) {
        User user = signedInUsernameGetter.getUser();
        Compliance compliance = getOrThrow(complianceId);
        validateUserOwnsCompliance(user, compliance);

        complianceRepository.deleteById(complianceId);
    }

    private void validateUserOwnsCompliance(User user, Compliance compliance) {
        if (compliance.getCompany().getId() != user.getCompany().getId()) {
            throw new ICException(ICErrorType.IC_701);
        }
    }
}
