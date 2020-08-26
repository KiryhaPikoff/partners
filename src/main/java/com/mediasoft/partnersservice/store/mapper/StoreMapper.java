package com.mediasoft.partnersservice.store.mapper;

import com.mediasoft.partnersservice.store.dto.StoreDto;
import com.mediasoft.partnersservice.store.model.Store;

public interface StoreMapper {

    StoreDto toDto(Store store);

    Store toEntity(StoreDto storeDto);
}