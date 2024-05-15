package com.ukma.controller;

import com.ukma.dto.type.TypeDto;
import com.ukma.dto.type.TypeListDto;
import com.ukma.service.TypeService;
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
@RequestMapping("/api/types")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TypeController {

    TypeService typeService;

    @PostMapping
    public TypeDto create(@Valid @RequestBody TypeDto typeDto) {
        return typeService.create(typeDto);
    }

    @GetMapping
    public TypeListDto findAll(@RequestParam(name = "page", required = false, defaultValue = "1")
                               Integer page,
                               @RequestParam(name = "limit", required = false, defaultValue = "" + Integer.MAX_VALUE)
                               Integer limit,
                               @RequestParam(name = "search", required = false)
                               String search) {
        return typeService.findAll(page, limit, search);
    }

    @PutMapping("/{id}")
    public TypeDto update(@PathVariable("id") Long id, @Valid @RequestBody TypeDto typeDto) {
        return typeService.update(id, typeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        typeService.deleteById(id);
    }
}
