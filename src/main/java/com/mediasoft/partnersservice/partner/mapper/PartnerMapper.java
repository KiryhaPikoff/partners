package com.mediasoft.partnersservice.partner.mapper;

import com.mediasoft.partnersservice.partner.dto.PartnerDto;
import com.mediasoft.partnersservice.partner.model.Partner;

public interface PartnerMapper {

    PartnerDto toDto(Partner partner);

    Partner toEntity(PartnerDto partnerDto);
}