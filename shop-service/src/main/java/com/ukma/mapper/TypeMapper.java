package com.ukma.mapper;

import com.ukma.dto.type.TypeDto;
import com.ukma.entity.Type;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TypeMapper {

    TypeDto toDto(Type type);
}
