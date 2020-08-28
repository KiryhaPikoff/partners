package com.mediasoft.partnersservice;

import com.mediasoft.partnersservice.common.exception.ResourceNotFoundException;
import com.mediasoft.partnersservice.partner.model.Partner;
import com.mediasoft.partnersservice.partner.repository.PartnerRepository;
import com.mediasoft.partnersservice.store.dto.StoreDto;
import com.mediasoft.partnersservice.store.mapper.StoreMapperImpl;
import com.mediasoft.partnersservice.store.model.Store;
import com.mediasoft.partnersservice.store.repository.StoreRepository;
import com.mediasoft.partnersservice.store.service.StoreServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private StoreMapperImpl storeMapper;

    @InjectMocks
    private StoreServiceImpl storeService;

    @Before
    public void setUp() {
        Mockito.when(storeMapper.toDto(Mockito.anyObject()))
                .thenCallRealMethod();
        Mockito.when(storeMapper.toEntity(Mockito.anyObject()))
                .thenCallRealMethod();
    }

    @Test
    public void create() {
        Mockito.when(partnerRepository.existsById(Mockito.anyLong()))
                .thenReturn(true);

        var storeDto = this.getStoreDto();
        storeService.create(storeDto);

        Mockito.verify(storeRepository, Mockito.times(1))
                .save(Mockito.anyObject());
    }

    @Test(expected = RuntimeException.class)
    public void createFailExistedName() {
        Mockito.when(storeRepository.existsByName(Mockito.anyString()))
                .thenReturn(true);

        var storeDto = this.getStoreDto();
        storeService.create(storeDto);

        Mockito.verify(storeRepository, Mockito.never())
                .save(Mockito.anyObject());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void createFailPartnerNotExists() {
        Mockito.when(storeRepository.existsByName(Mockito.anyString()))
                .thenReturn(false);
        Mockito.when(partnerRepository.existsById(Mockito.anyLong()))
                .thenReturn(false);

        var storeDto = this.getStoreDto();
        storeService.create(storeDto);

        Mockito.verify(storeRepository, Mockito.never())
                .save(Mockito.anyObject());
    }

    @Test
    public void getStoreNamesByPartnerId() {
        Mockito.when(storeRepository.getStoreNamesByPartnerId(Mockito.anyLong()))
                .thenReturn(this.mockNames());

        var actualNames = storeRepository.getStoreNamesByPartnerId(Mockito.anyLong());

        Mockito.verify(storeRepository, Mockito.times(1))
                .getStoreNamesByPartnerId(Mockito.anyLong());
        Assert.assertEquals(this.mockNames(), actualNames);
    }

    @Test
    public void getStoreNamesByPartnerIdEmptyList() {
        Mockito.when(storeRepository.getStoreNamesByPartnerId(Mockito.anyLong()))
                .thenReturn(Collections.emptyList());

        var actualNames = storeRepository.getStoreNamesByPartnerId(Mockito.anyLong());

        Mockito.verify(storeRepository, Mockito.times(1))
                .getStoreNamesByPartnerId(Mockito.anyLong());
        Assert.assertEquals(Collections.emptyList(), actualNames);
    }

    @Test
    public void getStoreNamesByNotExistedPartner() {
        var anyId = Mockito.anyLong();
        Mockito.when(storeRepository.getStoreNamesByPartnerId(anyId))
                .thenReturn(Collections.emptyList());

        var actualNames = storeRepository.getStoreNamesByPartnerId(anyId);

        Mockito.verify(storeRepository, Mockito.times(1))
                .getStoreNamesByPartnerId(anyId);
        Assert.assertEquals(Collections.emptyList(), actualNames);
    }

    @Test
    public void getStoreByPartnerId() {
        var anyId = Mockito.anyLong();
        Mockito.when(partnerRepository.existsById(anyId))
                .thenReturn(true);
        Mockito.when(storeRepository.existsByIdAndPartnerId(anyId, anyId))
                .thenReturn(true);
        Mockito.when(storeRepository.getOne(anyId))
                .thenReturn(Store.builder()
                        // In reality partner is always present.
                        // This need for storeMapper correct work.
                        .partner(Partner.builder().id(anyId).build())
                        .build());

        var store = storeService.getStoreByPartnerId(anyId, anyId);

        Assert.assertNotNull(store);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getStoreByNotExistedPartnerId() {
        var anyId = Mockito.anyLong();
        Mockito.when(partnerRepository.existsById(anyId))
                .thenReturn(false);

        storeService.getStoreByPartnerId(anyId, anyId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getStoreByNotExistedStoreId() {
        var anyId = Mockito.anyLong();
        Mockito.when(partnerRepository.existsById(anyId))
                .thenReturn(true);
        Mockito.when(storeRepository.existsByIdAndPartnerId(anyId, anyId))
                .thenReturn(false);

        storeService.getStoreByPartnerId(anyId, anyId);
    }

    @Ignore
    private StoreDto getStoreDto() {
        return StoreDto.builder()
                .name("name")
                .description("description")
                .address("address")
                .employeesNumber(15)
                .partnerId(0L)
                .build();
    }

    @Ignore
    private List<String> mockNames() {
        return List.of("1", "2", "3", "4", "5");
    }
}