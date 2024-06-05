package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.request.BoxDeleteRequest;
import com.itapp.inventorycontrol.dto.request.BoxRequest;
import com.itapp.inventorycontrol.dto.response.BoxResponse;
import com.itapp.inventorycontrol.entity.Box;
import com.itapp.inventorycontrol.mapper.BoxMapper;
import com.itapp.inventorycontrol.service.BoxService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/box")
public class BoxController {
    private final BoxService boxService;
    private final BoxMapper boxMapper;

    @GetMapping
    public ResponseEntity<List<BoxResponse>> getAll() {
        return new ResponseEntity<>(boxService.getAll().stream()
                .map(boxMapper::boxToResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<BoxResponse> getByUUID(@PathVariable("uuid") String uuid) {
        Box box = boxService.getBoxByUUID(uuid);

        return new ResponseEntity<>(
                boxMapper.boxToResponse(box),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BoxResponse> create(@RequestBody BoxRequest request) {
        Box box = boxService.create(request);

        return new ResponseEntity<>(
                boxMapper.boxToResponse(box),
                HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody BoxDeleteRequest request) {
        boxService.delete(request.getBoxId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
