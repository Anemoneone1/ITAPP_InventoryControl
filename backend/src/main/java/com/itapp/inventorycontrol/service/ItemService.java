package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.entity.*;
import com.itapp.inventorycontrol.exception.ICErrorType;
import com.itapp.inventorycontrol.exception.ICException;
import com.itapp.inventorycontrol.repository.ItemComplianceRepository;
import com.itapp.inventorycontrol.repository.ItemRepository;
import com.itapp.inventorycontrol.repository.StorageConditionItemRepository;
import com.itapp.inventorycontrol.security.SignedInUsernameGetter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class ItemService {
    private final SignedInUsernameGetter signedInUsernameGetter;
    private final ItemRepository itemRepository;
    private final ItemComplianceRepository itemComplianceRepository;
    private final StorageConditionItemRepository storageConditionItemRepository;
    private final ComplianceService complianceService;
    private final ItemComplianceService itemComplianceService;
    private final StorageConditionService storageConditionService;
    private final StorageConditionItemService storageConditionItemService;

    public Item getOrThrow(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ICException(ICErrorType.IC_401));
    }

    public List<Item> getAll() {
        User user = signedInUsernameGetter.getUser();
        return itemRepository.findAllByCompanyId(user.getCompany().getId());
    }

    public Item getById(Long itemId) {
        Item item = getOrThrow(itemId);
        User user = signedInUsernameGetter.getUser();
        validateUserOwnsItem(user, item);

        return item;
    }

    @Transactional
    public Item create(Item item, List<Long> complianceIds, List<Long> storageConditionIds) {
        User user = signedInUsernameGetter.getUser();
        item.setCompany(user.getCompany());

        // generate new ItemCompliances
        Set<Compliance> compliances = complianceService.getAllByIds(complianceIds, user.getCompany().getId());
        List<ItemCompliance> itemCompliances = compliances.stream()
                .map(compliance -> ItemCompliance.builder().compliance(compliance).item(item).build())
                .toList();

        // generate new StorageConditionItems
        Set<StorageCondition> storageConditions = storageConditionService.getAllByIds(storageConditionIds, user.getCompany().getId());
        List<StorageConditionItem> storageConditionItems = storageConditions.stream()
                .map(storageCondition -> StorageConditionItem.builder().storageCondition(storageCondition).item(item).build())
                .toList();

        // save data
        itemRepository.save(item);
        itemComplianceRepository.saveAll(itemCompliances);
        item.setCompliances(new HashSet<>(itemCompliances));
        storageConditionItemRepository.saveAll(storageConditionItems);
        item.setStorageConditions(new HashSet<>(storageConditionItems));

        return item;
    }

    @Transactional
    public Item edit(Item request, List<Long> complianceIds, List<Long> storageConditionIds) {
        User user = signedInUsernameGetter.getUser();
        Item item = getOrThrow(request.getId());
        validateUserOwnsItem(user, item);

        // generate new ItemCompliances
        Set<Compliance> compliances = complianceService.getAllByIds(complianceIds, user.getCompany().getId());
        List<ItemCompliance> itemCompliances = compliances.stream()
                .map(compliance -> ItemCompliance.builder().compliance(compliance).item(item).build())
                .toList();

        // generate new StorageConditionItems
        Set<StorageCondition> storageConditions = storageConditionService.getAllByIds(storageConditionIds, user.getCompany().getId());
        List<StorageConditionItem> storageConditionItems = storageConditions.stream()
                .map(storageCondition -> StorageConditionItem.builder().storageCondition(storageCondition).item(item).build())
                .toList();

        // delete existing links
        itemComplianceService.deleteAllByItemId(item.getId());
        storageConditionItemService.deleteAllByItemId(item.getId());

        // update data
        item.setCompany(user.getCompany());
        item.setLifetime(request.getLifetime());
        item.setName(request.getName());
        item.setDescription(request.getDescription());

        // save data
        itemRepository.save(item);
        itemComplianceRepository.saveAll(itemCompliances);
        item.setCompliances(new HashSet<>(itemCompliances));
        storageConditionItemRepository.saveAll(storageConditionItems);
        item.setStorageConditions(new HashSet<>(storageConditionItems));

        return item;
    }

    public void delete(Long id) {
        User user = signedInUsernameGetter.getUser();
        Item item = getOrThrow(id);
        validateUserOwnsItem(user, item);

        itemRepository.deleteById(id);
    }

    public void validateUserOwnsItem(User user, Item item) {
        if (item.getCompany().getId() != user.getCompany().getId()) {
            throw new ICException(ICErrorType.IC_401);
        }
    }
}
