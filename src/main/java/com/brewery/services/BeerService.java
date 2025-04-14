package com.brewery.services;

import com.brewery.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID beerId);

    BeerDto save(BeerDto beerDto);

    void updateBeer(BeerDto beerDto, UUID beerId);

    void deleteById(UUID beerId);

}
