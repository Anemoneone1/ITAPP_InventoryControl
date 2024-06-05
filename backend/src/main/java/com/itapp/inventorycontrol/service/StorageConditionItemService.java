package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.repository.StorageConditionItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StorageConditionItemService {
    private final StorageConditionItemRepository storageConditionItemRepository;

    public void deleteAllByItemId(Long itemId) {
        storageConditionItemRepository.deleteAllByItemId(itemId);
    }
}
