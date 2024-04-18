package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.request.StorageSpaceItemCreateRequest;
import com.itapp.inventorycontrol.dto.response.StorageSpaceItemResponse;
import com.itapp.inventorycontrol.entity.StorageSpaceItem;
import com.itapp.inventorycontrol.mapper.StorageSpaceItemMapper;
import com.itapp.inventorycontrol.service.StorageSpaceItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/storage-items")
public class StorageSpaceItemController {
    private final StorageSpaceItemService storageSpaceItemService;
    private final StorageSpaceItemMapper storageSpaceItemMapper;

    @GetMapping
    public ResponseEntity<List<StorageSpaceItemResponse>> getAll() {
        return new ResponseEntity<>(storageSpaceItemService.getAll().stream()
                .map(storageSpaceItemMapper::storageSpaceItemToResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StorageSpaceItemResponse> create(@RequestBody StorageSpaceItemCreateRequest request) {
        StorageSpaceItem storageSpaceItem = storageSpaceItemService.create(storageSpaceItemMapper.requestToStorageSpaceItem(request));

        return new ResponseEntity<>(
                storageSpaceItemMapper.storageSpaceItemToResponse(storageSpaceItem),
                HttpStatus.CREATED);
    }

//    @DeleteMapping
//    public ResponseEntity<Void> delete(@RequestBody StorageSpaceDeleteRequest request) {
//        storageSpaceService.delete(request.getStorageSpaceId());
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
