package com.brewery.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerDto {
    @Null
    private UUID id;

    @NotBlank
    @Size(min=3, max=100)
    private String beerName;

    @NotBlank
    private String beerStyle;

    @PositiveOrZero
    private Long upc;

    private OffsetDateTime createDate;
    private OffsetDateTime lastUpdatedDate;

}
