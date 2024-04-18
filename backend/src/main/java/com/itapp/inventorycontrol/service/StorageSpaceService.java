package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.entity.StorageCondition;
import com.itapp.inventorycontrol.entity.StorageConditionStorageSpace;
import com.itapp.inventorycontrol.entity.StorageSpace;
import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.exception.ICErrorType;
import com.itapp.inventorycontrol.exception.ICException;
import com.itapp.inventorycontrol.repository.StorageConditionStorageSpaceRepository;
import com.itapp.inventorycontrol.repository.StorageSpaceRepository;
import com.itapp.inventorycontrol.security.SignedInUsernameGetter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class StorageSpaceService {
    private final SignedInUsernameGetter signedInUsernameGetter;
    private final StorageSpaceRepository storageSpaceRepository;
    private final StorageConditionStorageSpaceRepository storageConditionStorageSpaceRepository;
    private final StorageConditionService storageConditionService;

    public StorageSpace getOrThrow(Long id) {
        return storageSpaceRepository.findById(id).orElseThrow(() -> new ICException(ICErrorType.IC_801));
    }

    public List<StorageSpace> getAll() {
        User user = signedInUsernameGetter.getUser();
        return storageSpaceRepository.findAllByWarehouseCompanyId(user.getCompany().getId());
    }

    public StorageSpace create(StorageSpace storageSpace, List<Long> storageConditionIds) {
        User user = signedInUsernameGetter.getUser();
        storageSpace.setFreeSpace(storageSpace.getSquareSpace());
        if (storageSpace.getParentStorageSpace().getId() == null) {
            storageSpace.setParentStorageSpace(null);
        }

        //TODO: check if warehouse in your company

        storageSpaceRepository.save(storageSpace);

        Set<StorageCondition> conditions = storageConditionService.getAllByIds(storageConditionIds, user.getCompany().getId());
        if (conditions.size() != storageConditionIds.size()) {
            throw new ICException(ICErrorType.IC_801);
        }
        List<StorageConditionStorageSpace> storageConditionStorageSpaces = conditions.stream()
                .map(condition -> StorageConditionStorageSpace.builder().storageCondition(condition).storageSpace(storageSpace).build())
                .toList();
        storageConditionStorageSpaceRepository.saveAll(storageConditionStorageSpaces);
        storageSpace.setStorageConditions(new HashSet<>(storageConditionStorageSpaces));

        return storageSpace;
    }

    @Transactional
    public void delete(Long id) {
        User user = signedInUsernameGetter.getUser();
        StorageSpace storageSpace = getOrThrow(id);
        validateUserOwnsStorageSpace(user, storageSpace);

        storageConditionStorageSpaceRepository.removeAllByStorageSpaceId(id);

        storageSpaceRepository.deleteById(id);
    }

    private void validateUserOwnsStorageSpace(User user, StorageSpace storageSpace) {
        if (storageSpace.getWarehouse().getCompany().getId() != user.getCompany().getId()) {
            throw new ICException(ICErrorType.IC_301);
        }
    }
}
