package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.entity.StorageCondition;
import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.exception.ICErrorType;
import com.itapp.inventorycontrol.exception.ICException;
import com.itapp.inventorycontrol.repository.StorageConditionRepository;
import com.itapp.inventorycontrol.security.SignedInUsernameGetter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class StorageConditionService {
    private final SignedInUsernameGetter signedInUsernameGetter;
    private final StorageConditionRepository storageConditionRepository;

    public StorageCondition getOrThrow(Long id) {
        return storageConditionRepository.findById(id).orElseThrow(() -> new ICException(ICErrorType.IC_601));
    }

    public List<StorageCondition> getAll() {
        User user = signedInUsernameGetter.getUser();
        return storageConditionRepository.findAllByCompanyId(user.getCompany().getId());
    }

    public StorageCondition create(StorageCondition storageCondition) {
        User user = signedInUsernameGetter.getUser();
        storageCondition.setCompany(user.getCompany());

        return storageConditionRepository.save(storageCondition);
    }

    public void delete(Long id) {
        User user = signedInUsernameGetter.getUser();
        StorageCondition storageCondition = getOrThrow(id);
        validateUserOwnsStorageCondition(user, storageCondition);

        storageConditionRepository.deleteById(id);
    }

    public Set<StorageCondition> getAllByIds(List<Long> storageConditionIds, Long companyId) {
        Set<StorageCondition> storageConditions = storageConditionRepository.findAllByCompanyIdAndIdIn(companyId, storageConditionIds);
        if (storageConditions.size() != storageConditionIds.size()) {
            throw new ICException(ICErrorType.IC_601);
        }
        return storageConditions;
    }

    private void validateUserOwnsStorageCondition(User user, StorageCondition storageCondition) {
        if (storageCondition.getCompany().getId() != user.getCompany().getId()) {
            throw new ICException(ICErrorType.IC_601);
        }
    }
}
