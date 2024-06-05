package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.entity.StorageConditionItem;
import com.itapp.inventorycontrol.entity.StorageConditionStorage;
import com.itapp.inventorycontrol.exception.ICErrorType;
import com.itapp.inventorycontrol.exception.ICException;
import com.itapp.inventorycontrol.repository.StorageConditionStorageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class StorageConditionStorageService {
    private final StorageConditionStorageRepository storageConditionStorageRepository;

    public void deleteAllByStorageId(Long storageId) {
        storageConditionStorageRepository.deleteAllByStorageId(storageId);
    }

    public void saveAll(List<StorageConditionStorage> storageConditionStorages) {
        storageConditionStorageRepository.saveAll(storageConditionStorages);
    }

    public void validateStorageConditionsOfStorage(Long storageId, Set<StorageConditionItem> storageConditions) {
        List<StorageConditionStorage> storageConditionStorages = storageConditionStorageRepository.findAllByStorageIdAndStorageConditionIdIn(
                storageId, storageConditions.stream().map(sci -> sci.getStorageCondition().getId()).toList());
        if (storageConditionStorages.size() != storageConditions.size()) {
            throw new ICException(ICErrorType.IC_803);
        }
    }
}
