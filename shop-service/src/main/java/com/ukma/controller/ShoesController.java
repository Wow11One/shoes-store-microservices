package com.ukma.controller;

import com.ukma.dto.shoes.ShoesListDto;
import com.ukma.dto.shoes.ShoesListItemDto;
import com.ukma.dto.shoes.ShoesPageDto;
import com.ukma.service.ShoesService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/shoes")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ShoesController {

    ShoesService shoesService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoesListItemDto create(@RequestParam(name = "name") String name,
                                   @RequestParam(name = "price") Integer price,
                                   @RequestParam(name = "brandId") Long brandId,
                                   @RequestParam(name = "typeId") Long typeId,
                                   @RequestParam(name = "info") String infoJsonArray,
                                   @RequestParam(name = "sizes") String sizesJsonArray,
                                   @RequestParam(name = "img") MultipartFile file) throws Exception {
        return shoesService.create(
                name,
                price,
                brandId,
                typeId,
                infoJsonArray,
                sizesJsonArray,
                file
        );
    }

    @GetMapping
    public ShoesListDto findAll(@RequestParam(name = "brandId", required = false)
                                Long brandId,
                                @RequestParam(name = "typeId", required = false)
                                Long typeId,
                                @RequestParam(name = "page", required = false, defaultValue = "1")
                                Integer page,
                                @RequestParam(name = "limit", required = false, defaultValue = "" + Integer.MAX_VALUE)
                                Integer limit,
                                @RequestParam(name = "search", required = false)
                                String search,
                                @RequestParam(name = "order", required = false)
                                String order) throws Exception {
        return shoesService.findAll(
                brandId,
                typeId,
                page,
                limit,
                search,
                order
        );
    }

    @GetMapping("/{id}")
    public ShoesPageDto findOne(@PathVariable("id") Long id) {
        return shoesService.findOne(id);
    }

    @PutMapping("/{id}")
    public ShoesListItemDto update(
            @PathVariable("id")
            Long id,
            @RequestParam("name")
            String name,
            @RequestParam("price")
            Integer price,
            @RequestParam("brandId")
            Long brandId,
            @RequestParam("typeId")
            Long typeId,
            @RequestParam("info")
            String infoJsonArray,
            @RequestParam("sizes")
            String sizesJsonArray,
            @RequestParam(value = "img", required = false)
            MultipartFile file) throws Exception {
        return shoesService.update(id, name, price, brandId, typeId, infoJsonArray, sizesJsonArray, file);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        shoesService.deleteById(id);
    }
}
