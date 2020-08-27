package com.mediasoft.partnersservice.store.mapper;

import com.mediasoft.partnersservice.partner.model.Partner;
import com.mediasoft.partnersservice.store.dto.StoreDto;
import com.mediasoft.partnersservice.store.model.Store;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StoreMapperImpl implements StoreMapper {

    @Override
    public StoreDto toDto(Store store) {
        return Objects.isNull(store) ? null :
                StoreDto.builder()
                        .id(store.getId())
                        .name(store.getName())
                        .description(store.getDescription())
                        .address(store.getAddress())
                        .employeesNumber(store.getEmployeesNumber())
                        .partnerId(store.getPartner().getId())
                        .build();
    }

    @Override
    public Store toEntity(StoreDto storeDto) {
        return Objects.isNull(storeDto) ? null :
                Store.builder()
                        .id(storeDto.getId())
                        .name(storeDto.getName())
                        .description(storeDto.getDescription())
                        .address(storeDto.getAddress())
                        .employeesNumber(storeDto.getEmployeesNumber())
                        .partner(Partner.builder()
                                    .id(storeDto.getPartnerId())
                                    .build())
                        .build();
    }
}