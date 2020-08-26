package com.mediasoft.partnersservice.store.service;

import com.mediasoft.partnersservice.store.dto.StoreDto;

import java.util.List;

public interface StoreService {

    void create(StoreDto storeDto);

    List<String> getStoreNamesByPartnerId(Long partnerId);

    StoreDto getStoreByPartnerId(Long partnerId, Long storeId);
}