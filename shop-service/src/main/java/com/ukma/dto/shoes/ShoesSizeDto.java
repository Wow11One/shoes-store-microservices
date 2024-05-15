package com.ukma.dto.shoes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoesSizeDto {

    Long id;
    Double sizeValue;
    @JsonProperty("shoes")
    ShoesListItemDto shoesListItemDto;
}
