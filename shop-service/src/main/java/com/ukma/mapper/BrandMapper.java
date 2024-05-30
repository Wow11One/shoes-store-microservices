package com.ukma.mapper;

import com.ukma.dto.brand.BrandDto;
import com.ukma.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BrandMapper {

    BrandDto toDto(Brand brand);
}
