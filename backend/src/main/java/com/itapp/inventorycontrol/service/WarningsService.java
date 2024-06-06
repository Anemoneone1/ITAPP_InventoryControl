package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.dto.warning.BoxWarning;
import com.itapp.inventorycontrol.dto.warning.ComplianceWarning;
import com.itapp.inventorycontrol.entity.Box;
import com.itapp.inventorycontrol.entity.Warehouse;
import com.itapp.inventorycontrol.mapper.BoxMapper;
import com.itapp.inventorycontrol.repository.BoxRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class WarningsService {
    private final BoxRepository boxRepository;

    public List<ComplianceWarning> getComplianceWarnings() {
        //TODO: implement
        return List.of();
    }

    public Map<Long, Integer> getWarningsForWarehouses(List<Warehouse> warehouses) {
        Map<Long, Integer> warnings = new HashMap<>();
        for (Warehouse warehouse : warehouses) {
            warnings.put(warehouse.getId(), getWarningsForWarehouse(warehouse.getId()).size());
        }
        return warnings;
    }

    public List<BoxWarning> getWarningsForWarehouse(Long warehouseId) {
        LocalDate now = LocalDate.now();
        List<Box> boxes = boxRepository.findAllBoxesWithWarningsByWarehouse(warehouseId, now);
        List<BoxWarning> warnings = new ArrayList<>(boxes.size());
        for (Box box : boxes) {
            if (box.getExpirationDate().isBefore(now)) {
                warnings.add(new BoxWarning(box.getId(), "Date expired"));
            } else {
                warnings.add(new BoxWarning(box.getId(), "Date will be expired soon"));
            }
        }
        return warnings;
    }
}
