package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.entity.Storage;
import com.itapp.inventorycontrol.entity.StorageCondition;
import com.itapp.inventorycontrol.entity.StorageConditionStorage;
import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.exception.ICErrorType;
import com.itapp.inventorycontrol.exception.ICException;
import com.itapp.inventorycontrol.repository.StorageConditionStorageRepository;
import com.itapp.inventorycontrol.repository.StorageRepository;
import com.itapp.inventorycontrol.security.SignedInUsernameGetter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class StorageService {
    private final SignedInUsernameGetter signedInUsernameGetter;
    private final StorageRepository storageRepository;
    private final StorageConditionService storageConditionService;
    private final WarehouseService warehouseService;
    private final StorageConditionStorageService storageConditionStorageService;

    public Storage getOrThrow(Long id) {
        return storageRepository.findById(id).orElseThrow(() -> new ICException(ICErrorType.IC_801));
    }

    public List<Storage> getAll() {
        User user = signedInUsernameGetter.getUser();
        return storageRepository.findAllByWarehouseCompanyId(user.getCompany().getId());
    }

    @Transactional
    public Storage create(Storage storage, List<Long> storageConditionIds) {
        User user = signedInUsernameGetter.getUser();
        warehouseService.validateUserOwnsWarehouse(user, storage.getWarehouse().getId());

        // generate new StorageConditionStorages
        Set<StorageCondition> conditions = storageConditionService.getAllByIds(storageConditionIds, user.getCompany().getId());
        List<StorageConditionStorage> storageConditionStorages = conditions.stream()
                .map(condition -> StorageConditionStorage.builder().storageCondition(condition).storage(storage).build())
                .toList();

        // save data
        storageRepository.save(storage);
        storageConditionStorageService.saveAll(storageConditionStorages);
        storage.setStorageConditions(new HashSet<>(storageConditionStorages));

        return storage;
    }

    @Transactional
    public Storage edit(Storage request, List<Long> storageConditionIds) {
        User user = signedInUsernameGetter.getUser();
        Storage storage = getOrThrow(request.getId());
        warehouseService.validateUserOwnsWarehouse(user, request.getWarehouse().getId());

        // generate new StorageConditionStorages
        Set<StorageCondition> conditions = storageConditionService.getAllByIds(storageConditionIds, user.getCompany().getId());
        List<StorageConditionStorage> storageConditionStorages = conditions.stream()
                .map(condition -> StorageConditionStorage.builder().storageCondition(condition).storage(storage).build())
                .toList();

        // delete existing links
        storageConditionStorageService.deleteAllByStorageId(storage.getId());

        // update data
        storage.setWarehouse(request.getWarehouse());
        storage.setName(request.getName());
        storage.setDescription(request.getDescription());

        // save data
        storageRepository.save(storage);
        storageConditionStorageService.saveAll(storageConditionStorages);
        storage.setStorageConditions(new HashSet<>(storageConditionStorages));

        return storage;
    }

    public void delete(Long id) {
        User user = signedInUsernameGetter.getUser();
        Storage storage = getOrThrow(id);
        warehouseService.validateUserOwnsWarehouse(user, storage.getWarehouse().getId());

        storageRepository.deleteById(id);
    }

    public void validateUserOwnsStorage(User user, Long storageId) {
        Storage storage = getOrThrow(storageId);
        if (storage.getWarehouse().getCompany().getId() != user.getCompany().getId()) {
            throw new ICException(ICErrorType.IC_801);
        }
    }
}
