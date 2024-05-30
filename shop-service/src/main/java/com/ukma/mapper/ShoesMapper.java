package com.ukma.mapper;

import com.ukma.dto.shoes.ShoesListItemDto;
import com.ukma.dto.shoes.ShoesPageDto;
import com.ukma.entity.Shoes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShoesMapper {

    ShoesListItemDto toListItemDto(Shoes shoes);

    ShoesPageDto toPageDto(Shoes shoes);
}
