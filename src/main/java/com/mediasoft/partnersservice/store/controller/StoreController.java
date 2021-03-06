package com.mediasoft.partnersservice.store.controller;

import com.mediasoft.partnersservice.store.dto.StoreDto;
import com.mediasoft.partnersservice.store.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${mapping.store_controller}")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    @PreAuthorize("@authDecider.canCreate(authentication)")
    public ResponseEntity create(@RequestBody @Valid StoreDto storeDto) {
        storeService.create(storeDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{partner_id}")
    public ResponseEntity<List<String>> getStoreNamesByPartnerId(
            @PathVariable("partner_id") Long partnerId) {
        var names = storeService.getStoreNamesByPartnerId(partnerId);
        return new ResponseEntity<>(names, HttpStatus.OK);
    }

    @GetMapping("/{partner_id}/{store_id}")
    public ResponseEntity<StoreDto> getStoreByPartnerId(@PathVariable("partner_id") Long partnerId,
                                                        @PathVariable("store_id")   Long storeId) {
        var store = storeService.getStoreByPartnerId(partnerId, storeId);
        return new ResponseEntity<>(store, HttpStatus.OK);
    }
}