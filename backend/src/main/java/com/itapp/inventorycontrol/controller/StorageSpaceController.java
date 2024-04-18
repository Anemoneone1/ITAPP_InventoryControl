package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.request.StorageSpaceCreateRequest;
import com.itapp.inventorycontrol.dto.request.StorageSpaceDeleteRequest;
import com.itapp.inventorycontrol.dto.response.StorageSpaceResponse;
import com.itapp.inventorycontrol.entity.StorageSpace;
import com.itapp.inventorycontrol.mapper.StorageSpaceMapper;
import com.itapp.inventorycontrol.service.StorageSpaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/storage")
public class StorageSpaceController {
    private final StorageSpaceService storageSpaceService;
    private final StorageSpaceMapper storageSpaceMapper;

    @GetMapping
    public ResponseEntity<List<StorageSpaceResponse>> getAll() {
        return new ResponseEntity<>(storageSpaceService.getAll().stream()
                .map(storageSpaceMapper::storageSpaceToResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StorageSpaceResponse> create(@RequestBody StorageSpaceCreateRequest request) {
        StorageSpace storageSpace = storageSpaceService.create(storageSpaceMapper.requestToStorageSpace(request), request.getStorageConditionIds());

        return new ResponseEntity<>(
                storageSpaceMapper.storageSpaceToResponse(storageSpace),
                HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody StorageSpaceDeleteRequest request) {
        storageSpaceService.delete(request.getStorageSpaceId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
