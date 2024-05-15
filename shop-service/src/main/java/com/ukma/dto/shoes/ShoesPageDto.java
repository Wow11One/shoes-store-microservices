package com.ukma.dto.shoes;

import com.ukma.dto.brand.BrandDto;
import com.ukma.dto.type.TypeDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
public class ShoesPageDto extends ShoesListItemDto {

    List<ShoesInfoDto> info;
    List<ShoesSizeDto> sizes;

    public ShoesPageDto(Long id,
                        String name,
                        Integer price,
                        String image,
                        Instant createdAt,
                        BrandDto brandDto,
                        TypeDto typeDto) {
        super(id, name, price, image, createdAt, brandDto, typeDto);
    }
}
