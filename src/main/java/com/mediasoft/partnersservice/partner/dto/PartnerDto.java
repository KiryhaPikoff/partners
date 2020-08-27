package com.mediasoft.partnersservice.partner.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class PartnerDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @NotNull
    private final String name;

    @NotNull
    private final String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Integer storesCount;
}