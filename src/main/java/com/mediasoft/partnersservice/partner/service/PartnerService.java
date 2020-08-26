package com.mediasoft.partnersservice.partner.service;

import com.mediasoft.partnersservice.partner.dto.PartnerDto;

import java.util.List;

public interface PartnerService {

    void create(PartnerDto partnerDto);

    List<String> getPartnerNames();

    PartnerDto getById(Long id);
}