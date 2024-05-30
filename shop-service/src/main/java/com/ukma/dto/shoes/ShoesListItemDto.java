package com.ukma.dto.shoes;

import com.ukma.dto.brand.BrandDto;
import com.ukma.dto.type.TypeDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoesListItemDto {

    Long id;
    String name;
    Integer price;
    String image;
    Instant createdAt;
    BrandDto brand;
    TypeDto type;
}
