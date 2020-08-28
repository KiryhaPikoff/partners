package com.mediasoft.partnersservice.store.service;

import com.mediasoft.partnersservice.common.exception.ResourceNotFoundException;
import com.mediasoft.partnersservice.partner.repository.PartnerRepository;
import com.mediasoft.partnersservice.store.dto.StoreDto;
import com.mediasoft.partnersservice.store.mapper.StoreMapper;
import com.mediasoft.partnersservice.store.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    private final StoreMapper storeMapper;

    private final PartnerRepository partnerRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository,
                            StoreMapper storeMapper,
                            PartnerRepository partnerRepository) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
        this.partnerRepository = partnerRepository;
    }

    @Override
    public void create(StoreDto storeDto) {
        var store = storeMapper.toEntity(storeDto);
        if (this.isNameNotUnique(store.getName())) {
            throw new RuntimeException(
                    "Store with name=" + store.getName() + " already exists."
            );
        }

        var partnerId = store.getPartner().getId();
        if (!partnerRepository.existsById(partnerId)) {
            throw new ResourceNotFoundException(
                    "Partner with id=" + partnerId + " not exists."
            );
        }

        storeRepository.save(store);
    }

    @Override
    public List<String> getStoreNamesByPartnerId(Long partnerId) {
        return storeRepository.getStoreNamesByPartnerId(partnerId);
    }

    @Override
    public StoreDto getStoreByPartnerId(Long partnerId, Long storeId) {
        if (!partnerRepository.existsById(partnerId)) {
            throw new ResourceNotFoundException(
                    "Partner with id=" + partnerId + " not exists."
            );
        }

        if (!storeRepository.existsByIdAndPartnerId(storeId, partnerId)) {
            throw new ResourceNotFoundException(
                    "Partner with id=" + partnerId +
                    " doesn't have store with id=" + storeId + "."
            );
        }

        var store = storeRepository.getOne(storeId);
        return storeMapper.toDto(store);
    }

    private boolean isNameNotUnique(String partnerName) {
        return storeRepository.existsByName(partnerName);
    }
}