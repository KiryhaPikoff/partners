package com.mediasoft.partnersservice.partner.service;

import com.mediasoft.partnersservice.partner.dto.PartnerDto;

import java.util.List;
import java.util.Optional;

public interface PartnerService {

    void create(PartnerDto partnerDto);

    List<String> getPartnerNames();

    Optional<PartnerDto> getById(Long id);
}