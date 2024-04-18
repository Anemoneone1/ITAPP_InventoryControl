package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.request.StorageConditionCreateRequest;
import com.itapp.inventorycontrol.dto.request.StorageConditionDeleteRequest;
import com.itapp.inventorycontrol.dto.response.StorageConditionResponse;
import com.itapp.inventorycontrol.entity.StorageCondition;
import com.itapp.inventorycontrol.mapper.StorageConditionMapper;
import com.itapp.inventorycontrol.service.StorageConditionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/storage-condition")
public class StorageConditionController {
    private final StorageConditionService storageConditionService;
    private final StorageConditionMapper storageConditionMapper;

    @GetMapping
    public ResponseEntity<List<StorageConditionResponse>> getAll() {
        return new ResponseEntity<>(storageConditionService.getAll().stream()
                .map(storageConditionMapper::StorageConditionToResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StorageConditionResponse> create(@RequestBody StorageConditionCreateRequest request) {
        StorageCondition storageCondition = storageConditionService.create(
                storageConditionMapper.requestToStorageCondition(request));

        return new ResponseEntity<>(
                storageConditionMapper.StorageConditionToResponse(storageCondition),
                HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody StorageConditionDeleteRequest request) {
        storageConditionService.delete(request.getConditionId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
