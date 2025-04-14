package com.brewery.services.v2;

import com.brewery.web.model.BeerDto;
import com.brewery.web.model.v2.BeerDtoV2;

import java.util.UUID;

public interface BeerServiceV2 {

    BeerDto getBeerById(UUID beerId);

    BeerDto save(BeerDtoV2 beerDto);

    void updateBeer(BeerDtoV2 beerDto, UUID beerId);

    void deleteById(UUID beerId);
}
