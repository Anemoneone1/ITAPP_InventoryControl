package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.entity.Warehouse;
import com.itapp.inventorycontrol.exception.ICErrorType;
import com.itapp.inventorycontrol.exception.ICException;
import com.itapp.inventorycontrol.repository.WarehouseRepository;
import com.itapp.inventorycontrol.security.SignedInUsernameGetter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class WarehouseService {
    private final SignedInUsernameGetter signedInUsernameGetter;
    private final WarehouseRepository warehouseRepository;

    public Warehouse getOrThrow(Long id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new ICException(ICErrorType.IC_301));
    }

    public List<Warehouse> getAll() {
        User user = signedInUsernameGetter.getUser();
        return warehouseRepository.findAllByCompanyId(user.getCompany().getId());
    }

    public Warehouse create(Warehouse warehouse) {
        User user = signedInUsernameGetter.getUser();
        warehouse.setCompany(user.getCompany());

        return warehouseRepository.save(warehouse);
    }

    public Warehouse edit(Warehouse request) {
        User user = signedInUsernameGetter.getUser();
        Warehouse warehouse = getOrThrow(request.getId());
        validateUserOwnsWarehouse(user, warehouse);

        warehouse.setName(request.getName());
        warehouse.setAddress(request.getAddress());

        return warehouseRepository.save(warehouse);
    }

    public void delete(Long warehouseId) {
        User user = signedInUsernameGetter.getUser();
        Warehouse warehouse = getOrThrow(warehouseId);
        validateUserOwnsWarehouse(user, warehouse);

        warehouseRepository.deleteById(warehouseId);
    }

    private void validateUserOwnsWarehouse(User user, Warehouse warehouse) {
        if (warehouse.getCompany().getId() != user.getCompany().getId()) {
            throw new ICException(ICErrorType.IC_301);
        }
    }
}
