package com.ukma.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ukma.dto.shoes.ShoesSizeDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class OrderShoesSizeDto {

    Long id;
    Integer amount;
    @JsonProperty("shoesSize")
    ShoesSizeDto shoesSizeDto;
}
