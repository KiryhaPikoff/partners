package com.mediasoft.partnersservice.partner.service;

import com.mediasoft.partnersservice.partner.dto.PartnerDto;
import com.mediasoft.partnersservice.partner.mapper.PartnerMapper;
import com.mediasoft.partnersservice.partner.repository.PartnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;

    private final PartnerMapper partnerMapper;

    @Override
    public void create(PartnerDto partnerDto) {
        var partner = partnerMapper.toEntity(partnerDto);
        if (this.isNameNotUnique(partner.getName())) {
            throw new RuntimeException(
                    "Partner with name=" + partner.getName() + " already exists."
            );
        }
        partnerRepository.save(partner);
    }

    @Override
    public List<String> getPartnerNames() {
        return partnerRepository.getPartnerNames();
    }

    @Override
    @Transactional
    public Optional<PartnerDto> getById(Long id) {
        var partner = partnerRepository.getOne(id);
        return Optional.ofNullable(partnerMapper.toDto(partner));
    }

    private boolean isNameNotUnique(String partnerName) {
        return partnerRepository.existsByName(partnerName);
    }
}