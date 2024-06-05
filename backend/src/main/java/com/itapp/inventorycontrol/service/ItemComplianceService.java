package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.repository.ItemComplianceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ItemComplianceService {
    private final ItemComplianceRepository itemComplianceRepository;

    public void deleteAllByItemId(Long itemId) {
        itemComplianceRepository.deleteAllByItemId(itemId);
    }
}
