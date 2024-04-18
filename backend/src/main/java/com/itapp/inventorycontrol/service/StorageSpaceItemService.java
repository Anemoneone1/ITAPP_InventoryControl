package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.entity.Item;
import com.itapp.inventorycontrol.entity.StorageSpaceItem;
import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.exception.ICErrorType;
import com.itapp.inventorycontrol.exception.ICException;
import com.itapp.inventorycontrol.repository.StorageSpaceItemRepository;
import com.itapp.inventorycontrol.security.SignedInUsernameGetter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class StorageSpaceItemService {
    private final SignedInUsernameGetter signedInUsernameGetter;
    private final StorageSpaceItemRepository storageSpaceItemRepository;
    private final ItemService itemService;

//    public StorageSpaceItem getOrThrow(Long id) {
//        return storageSpaceItemRepository.findById(id).orElseThrow(() -> new ICException(ICErrorType.IC_802));
//    }

    public List<StorageSpaceItem> getAll() {
        User user = signedInUsernameGetter.getUser();
        return storageSpaceItemRepository.findAllByItemCompanyId(user.getCompany().getId());
    }

    public StorageSpaceItem create(StorageSpaceItem storageSpaceItem) {
        //TODO: check if item and storage space are in company

        Item item = itemService.getOrThrow(storageSpaceItem.getItem().getId());
        storageSpaceItem.setExpirationDate(Date.from(storageSpaceItem.getCreationDate().toInstant().plus(item.getLifetime(), ChronoUnit.DAYS)));
        storageSpaceItem.setItem(item);

        return storageSpaceItemRepository.save(storageSpaceItem);
    }

//    @Transactional
//    public void delete(Long id) {
//        User user = signedInUsernameGetter.getUser();
//        StorageSpace storageSpace = getOrThrow(id);
//        validateUserOwnsStorageSpace(user, storageSpace);
//
//        storageConditionStorageSpaceRepository.removeAllByStorageSpaceId(id);
//
//        storageSpaceRepository.deleteById(id);
//    }

//    private void validateUserOwnsStorageSpace(User user, StorageSpace storageSpace) {
//        if (storageSpace.getWarehouse().getCompany().getId() != user.getCompany().getId()) {
//            throw new ICException(ICErrorType.IC_802);
//        }
//    }
}
