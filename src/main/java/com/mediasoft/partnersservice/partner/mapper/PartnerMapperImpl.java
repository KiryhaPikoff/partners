package com.mediasoft.partnersservice.partner.mapper;

import com.mediasoft.partnersservice.partner.dto.PartnerDto;
import com.mediasoft.partnersservice.partner.model.Partner;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PartnerMapperImpl implements PartnerMapper {

    @Override
    public PartnerDto toDto(Partner partner) {
        return Objects.isNull(partner) ? null :
                PartnerDto.builder()
                        .id(partner.getId())
                        .name(partner.getName())
                        .description(partner.getDescription())
                        .storesCount(partner.getStoresCount())
                        .build();
    }

    @Override
    public Partner toEntity(PartnerDto partnerDto) {
        return Objects.isNull(partnerDto) ? null :
                Partner.builder()
                        .id(partnerDto.getId())
                        .name(partnerDto.getName())
                        .description(partnerDto.getDescription())
                        .build();
    }
}