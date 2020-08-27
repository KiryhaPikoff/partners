package com.mediasoft.partnersservice.store.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class StoreDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @NotNull
    private final String name;

    @NotNull
    private final String description;

    @NotNull
    private final String address;

    @Min(0)
    @NotNull
    private final Integer employeesNumber;

    @Min(0)
    @NotNull
    @JsonProperty("partner_id")
    private final Long partnerId;
}