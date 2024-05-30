package com.ukma.controller;

import com.ukma.dto.brand.BrandDto;
import com.ukma.dto.brand.BrandListDto;
import com.ukma.service.BrandService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brands")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BrandController {

    BrandService brandService;

    @PostMapping
    public BrandDto create(@Valid @RequestBody BrandDto brandDto) {
        return brandService.create(brandDto);
    }

    @GetMapping
    public BrandListDto findAll(@RequestParam(name = "page", required = false, defaultValue = "1")
                                Integer page,
                                @RequestParam(name = "limit", required = false, defaultValue = "" + Integer.MAX_VALUE)
                                Integer limit,
                                @RequestParam(name = "search", required = false)
                                String search) {
        return brandService.findAll(page, limit, search);
    }

    @PutMapping("/{id}")
    public BrandDto update(@PathVariable("id") Long id, @Valid @RequestBody BrandDto brandDto) {
        return brandService.update(id, brandDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        brandService.deleteById(id);
    }
}
