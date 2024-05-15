package com.ukma.service;

import com.ukma.dto.brand.BrandDto;
import com.ukma.dto.brand.BrandListDto;
import com.ukma.entity.Brand;
import com.ukma.mapper.BrandMapper;
import com.ukma.repository.BrandRepository;
import com.ukma.service.specification.BrandSpecifications;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BrandService {

    BrandRepository brandRepository;
    BrandMapper brandMapper;

    @Transactional
    public BrandDto create(BrandDto brandDto) {
        Brand brand = new Brand();
        brand.setName(brandDto.getName());
        brandRepository.save(brand);

        return brandMapper.toDto(brand);
    }

    @Transactional(readOnly = true)
    public BrandListDto findAll(Integer page, Integer limit, String search) {
        Specification<Brand> specification = buildSpecification(search);
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("id"));

        Page<Brand> brandPage = brandRepository.findAll(specification, pageable);
        List<BrandDto> brandDtoList = brandPage.getContent()
                .stream()
                .map(brandMapper::toDto)
                .toList();

        return new BrandListDto(brandDtoList, (int) brandPage.getTotalElements());
    }

    @Transactional
    public BrandDto update(Long id, BrandDto brandDto) {
        Brand brand = brandRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("brand with such id does not exist")
        );

        brand.setName(brandDto.getName());
        brandRepository.save(brand);

        return brandMapper.toDto(brand);
    }

    @Transactional
    public void deleteById(Long id) {
        brandRepository.deleteById(id);
    }

    private Specification<Brand> buildSpecification(String search) {
        List<Specification<Brand>> specifications = new ArrayList<>();
        if (search != null && !search.trim().isEmpty()) {
            specifications.add(BrandSpecifications.searchSpecification(search));
        }

        return BrandSpecifications.specificationUnion(specifications);
    }
}
