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
    private final StorageConditionService storageConditionService;

    public Item getOrThrow(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ICException(ICErrorType.IC_401));
    }

    public List<Item> getAll() {
        User user = signedInUsernameGetter.getUser();
        return itemRepository.findAllByCompanyId(user.getCompany().getId());
    }

    @Transactional
    public Item create(Item item, List<Long> complianceIds, List<Long> storageConditionIds) {
        User user = signedInUsernameGetter.getUser();
        item.setCompany(user.getCompany());
        itemRepository.save(item);

        Set<Compliance> compliances = complianceService.getAllByIds(complianceIds, user.getCompany().getId());
        if (compliances.size() != complianceIds.size()) {
            throw new ICException(ICErrorType.IC_701);
        }
        List<ItemCompliance> itemCompliances = compliances.stream()
                .map(compliance -> ItemCompliance.builder().compliance(compliance).item(item).build())
                .toList();
        itemComplianceRepository.saveAll(itemCompliances);
        item.setCompliances(new HashSet<>(itemCompliances));

        Set<StorageCondition> storageConditions = storageConditionService.getAllByIds(storageConditionIds, user.getCompany().getId());
        if (storageConditions.size() != storageConditionIds.size()) {
            throw new ICException(ICErrorType.IC_601);
        }
        List<StorageConditionItem> storageConditionItems = storageConditions.stream()
                .map(storageCondition -> StorageConditionItem.builder().storageCondition(storageCondition).item(item).build())
                .toList();
        storageConditionItemRepository.saveAll(storageConditionItems);
        item.setStorageConditions(new HashSet<>(storageConditionItems));

        return item;
    }

    public Item edit(Item request) {
        User user = signedInUsernameGetter.getUser();
        Item item = getOrThrow(request.getId());
        validateUserOwnsItem(user, item);

        //TODO: delete previous connections and create new ones

        return itemRepository.save(item);
    }

    public void delete(Long id) {
        User user = signedInUsernameGetter.getUser();
        Item item = getOrThrow(id);
        validateUserOwnsItem(user, item);

        itemRepository.deleteById(id);
    }

    private void validateUserOwnsItem(User user, Item item) {
        if (item.getCompany().getId() != user.getCompany().getId()) {
            throw new ICException(ICErrorType.IC_401);
        }
    }
}
