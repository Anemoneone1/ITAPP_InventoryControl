package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.dto.request.BoxRequest;
import com.itapp.inventorycontrol.entity.*;
import com.itapp.inventorycontrol.exception.ICErrorType;
import com.itapp.inventorycontrol.exception.ICException;
import com.itapp.inventorycontrol.repository.BoxItemRepository;
import com.itapp.inventorycontrol.repository.BoxRepository;
import com.itapp.inventorycontrol.security.SignedInUsernameGetter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.*;

@AllArgsConstructor
@Service
public class BoxService {
    private final SignedInUsernameGetter signedInUsernameGetter;
    private final BoxRepository boxRepository;
    private final BoxItemRepository boxItemRepository;
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

        Box box = new Box();
        box.setStorage(Storage.builder().id(request.getStorageId()).build());
        box.setSerialNumber(request.getSerialNumber());
        box = boxRepository.save(box);
        box.setUuid(UUID.nameUUIDFromBytes(box.getId().toString().getBytes()).toString());

        Date expirationDate = Date.from(request.getCreationDate().toInstant().plus(item.getLifetime(), ChronoUnit.DAYS));
        List<BoxItem> boxItems = new ArrayList<>(request.getItemNum());
        for (int i = 0; i < request.getItemNum(); i++) {
            boxItems.add(BoxItem.builder()
                    .box(box).item(item).creationDate(request.getCreationDate()).expirationDate(expirationDate)
                    .build());
        }
        boxItemRepository.saveAll(boxItems);
        box.setItems(new HashSet<>(boxItems));

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
