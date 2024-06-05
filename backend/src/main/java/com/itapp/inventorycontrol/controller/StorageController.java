package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.request.StorageCreateRequest;
import com.itapp.inventorycontrol.dto.request.StorageDeleteRequest;
import com.itapp.inventorycontrol.dto.request.StorageEditRequest;
import com.itapp.inventorycontrol.dto.response.StorageResponse;
import com.itapp.inventorycontrol.entity.Storage;
import com.itapp.inventorycontrol.mapper.StorageMapper;
import com.itapp.inventorycontrol.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/storage")
public class StorageController {
    private final StorageService storageService;
    private final StorageMapper storageMapper;

    @GetMapping
    public ResponseEntity<List<StorageResponse>> getAll() {
        return new ResponseEntity<>(storageService.getAll().stream()
                .map(storageMapper::storageSpaceToResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StorageResponse> create(@RequestBody StorageCreateRequest request) {
        Storage storage = storageService.create(storageMapper.requestToStorageSpace(request), request.getStorageConditionIds());

        return new ResponseEntity<>(
                storageMapper.storageSpaceToResponse(storage),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<StorageResponse> edit(@RequestBody StorageEditRequest request) {
        Storage storage = storageService.edit(storageMapper.requestToStorageSpace(request), request.getStorageConditionIds());

        return new ResponseEntity<>(
                storageMapper.storageSpaceToResponse(storage),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody StorageDeleteRequest request) {
        storageService.delete(request.getStorageId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
