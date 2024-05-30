package com.ukma.mapper;

import com.ukma.dto.order.OrderDetailedDto;
import com.ukma.dto.order.OrderDto;
import com.ukma.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderDto toDto(Order order);
    OrderDetailedDto toDetailedDto(Order order);
}
