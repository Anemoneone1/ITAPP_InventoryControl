package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.repository.StorageConditionStorageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StorageConditionStorageService {
    private final StorageConditionStorageRepository storageConditionStorageRepository;

    public void deleteAllByStorageId(Long storageId) {
        storageConditionStorageRepository.deleteAllByStorageId(storageId);
    }
}
