package com.mediasoft.partnersservice.partner.controller;

import com.mediasoft.partnersservice.common.exception.ResourceNotFoundException;
import com.mediasoft.partnersservice.partner.dto.PartnerDto;
import com.mediasoft.partnersservice.partner.service.PartnerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${mapping.partner_controller}")
public class PartnerController {

    private final PartnerService partnerService;

    @Autowired
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping
    @PreAuthorize("@authDecider.canCreate(authentication)")
    public ResponseEntity create(@RequestBody @Valid PartnerDto partnerDto) {
        partnerService.create(partnerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<String>> getPartnerNames() {
        var names = partnerService.getPartnerNames();
        return new ResponseEntity<>(names, HttpStatus.OK);
    }

    @GetMapping("/{partner_id}")
    public ResponseEntity<PartnerDto> getPartnerById(
            @PathVariable("partner_id") Long partnerId) {
        var partner = partnerService.getById(partnerId);
        if (partner.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Partner with id=" + partnerId + " not found."
            );
        }
        return new ResponseEntity<>(partner.get(), HttpStatus.OK);
    }
}