package com.brewery.web.mappers;

import com.brewery.domain.Beer;
import com.brewery.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    Beer beerdtoToBeer(BeerDto beerDto);
}
