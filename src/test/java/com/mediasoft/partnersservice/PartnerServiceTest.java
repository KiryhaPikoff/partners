package com.mediasoft.partnersservice;

import com.mediasoft.partnersservice.partner.dto.PartnerDto;
import com.mediasoft.partnersservice.partner.mapper.PartnerMapperImpl;
import com.mediasoft.partnersservice.partner.model.Partner;
import com.mediasoft.partnersservice.partner.repository.PartnerRepository;
import com.mediasoft.partnersservice.partner.service.PartnerServiceImpl;
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
public class PartnerServiceTest {

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private PartnerMapperImpl partnerMapper;

    @InjectMocks
    private PartnerServiceImpl partnerService;

    @Before
    public void setUp() {
        Mockito.when(partnerMapper.toDto(Mockito.anyObject()))
                .thenCallRealMethod();
        Mockito.when(partnerMapper.toEntity(Mockito.anyObject()))
                .thenCallRealMethod();
    }

    @Test
    public void create() {
        Mockito.when(partnerRepository.existsByName(Mockito.anyString()))
                .thenReturn(false);

        var partnerDto = this.getPartnerDto();
        partnerService.create(partnerDto);

        Mockito.verify(partnerRepository, Mockito.times(1))
                .save(Mockito.anyObject());
    }

    @Test(expected = RuntimeException.class)
    public void createFailExistedName() {
        Mockito.when(partnerRepository.existsByName(Mockito.anyString()))
                .thenReturn(true);

        var partnerDto = this.getPartnerDto();
        partnerService.create(partnerDto);

        Mockito.verify(partnerRepository, Mockito.never())
                .save(Mockito.anyObject());
    }

    @Test
    public void getPartnerNames() {
        Mockito.when(partnerRepository.getPartnerNames())
                .thenReturn(this.mockNames());

        var actualNames = partnerService.getPartnerNames();

        Mockito.verify(partnerRepository, Mockito.times(1))
                .getPartnerNames();
        Assert.assertEquals(this.mockNames(), actualNames);
    }

    @Test
    public void getEmptyPartnerNames() {
        Mockito.when(partnerRepository.getPartnerNames())
                .thenReturn(Collections.emptyList());

        var actualNames = partnerService.getPartnerNames();

        Mockito.verify(partnerRepository, Mockito.times(1))
                .getPartnerNames();
        Assert.assertEquals(Collections.emptyList(), actualNames);
    }

    @Test
    public void getById() {
        var id = Mockito.anyLong();
        Mockito.when(partnerRepository.getOne(id))
                .thenReturn(Partner.builder().build());

        var partner = partnerService.getById(id);

        Assert.assertNotNull(partner);
        Assert.assertTrue(partner.isPresent());
    }

    @Test
    public void getByIdNotExist() {
        var id = Mockito.anyLong();
        Mockito.when(partnerRepository.getOne(id))
                .thenReturn(null);

        var partner = partnerService.getById(id);

        Assert.assertNotNull(partner);
        Assert.assertTrue(partner.isEmpty());
    }

    @Ignore
    private PartnerDto getPartnerDto() {
        return PartnerDto.builder()
                .name("name")
                .description("description")
                .storesCount(15)
                .build();
    }

    @Ignore
    private List<String> mockNames() {
        return List.of("1", "2", "3", "4", "5");
    }
}