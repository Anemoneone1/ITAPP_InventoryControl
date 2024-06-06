package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.dto.request.BoxRequest;
import com.itapp.inventorycontrol.entity.Box;
import com.itapp.inventorycontrol.entity.Item;
import com.itapp.inventorycontrol.entity.Storage;
import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.exception.ICErrorType;
import com.itapp.inventorycontrol.exception.ICException;
import com.itapp.inventorycontrol.repository.BoxRepository;
import com.itapp.inventorycontrol.security.SignedInUsernameGetter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class BoxService {
    private final SignedInUsernameGetter signedInUsernameGetter;
    private final BoxRepository boxRepository;
    private final ItemService itemService;
    private final StorageService storageService;
    private final ComplianceAgreementService complianceAgreementService;
    private final StorageConditionStorageService storageConditionStorageService;

    public Box getOrThrow(Long id) {
        return boxRepository.findById(id).orElseThrow(() -> new ICException(ICErrorType.IC_802));
    }

    public List<Box> getAll() {
        User user = signedInUsernameGetter.getUser();
        return boxRepository.findAllByStorageWarehouseCompanyId(user.getCompany().getId());
    }

    public Box getBoxByUUID(String UUID) {
        return boxRepository.findByUuid(UUID).orElseThrow(() -> new ICException(ICErrorType.IC_802));
    }

    @Transactional
    public Box create(BoxRequest request) {
        User user = signedInUsernameGetter.getUser();

        // check storage is in your warehouse
        storageService.validateUserOwnsStorage(user, request.getStorageId());

        Item item = itemService.getOrThrow(request.getItemId());
        // check item is in you db
        itemService.validateUserOwnsItem(user, item);

        // check compliances
        complianceAgreementService.validateComplianceAgreementsActive(item.getCompliances());
        // check storage conditions
        storageConditionStorageService.validateStorageConditionsOfStorage(request.getStorageId(), item.getStorageConditions());

        Box box = Box.builder()
                .storage(Storage.builder().id(request.getStorageId()).build())
                .serialNumber(request.getSerialNumber())
                .item(item)
                .amount(request.getItemNum())
                .creationDate(request.getCreationDate())
                .expirationDate(request.getCreationDate().plus(item.getLifetime(), ChronoUnit.DAYS))
                .build();
        box = boxRepository.save(box);
        box.setUuid(UUID.nameUUIDFromBytes(box.getId().toString().getBytes()).toString());

        return box;
    }

    @Transactional
    public void delete(Long id) {
        User user = signedInUsernameGetter.getUser();
        Box box = getOrThrow(id);
        validateUserOwnsBox(user, box.getStorage());

        boxRepository.deleteById(id);
    }

    private void validateUserOwnsBox(User user, Storage storage) {
        if (storage.getWarehouse().getCompany().getId() != user.getCompany().getId()) {
            throw new ICException(ICErrorType.IC_802);
        }
    }
}
