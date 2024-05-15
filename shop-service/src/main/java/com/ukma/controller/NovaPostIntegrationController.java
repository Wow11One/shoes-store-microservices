package com.ukma.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ukma.dto.nova.post.NovaPostItemDto;
import com.ukma.service.NovaPostIntegrationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nova-post")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NovaPostIntegrationController {

    NovaPostIntegrationService novaPostIntegrationService;

    @GetMapping("/regions")
    public List<NovaPostItemDto> findAllRegions() throws JsonProcessingException {
        return novaPostIntegrationService.findAllRegions();
    }

    @GetMapping("/cities")
    public List<NovaPostItemDto> findAllCitiesByRegion(@RequestParam("regionId") String regionId)
            throws JsonProcessingException {
        return novaPostIntegrationService.findAllCitiesByRegion(regionId);
    }

    @GetMapping("/departments")
    public List<NovaPostItemDto> findAllDepartmentsByCity(@RequestParam("cityId") String cityId)
            throws JsonProcessingException {
        return novaPostIntegrationService.findAllDepartmentsByCity(cityId);
    }

    @GetMapping("/regions/{id}")
    public NovaPostItemDto findRegionById(@PathVariable("id") String id) throws JsonProcessingException {
        return novaPostIntegrationService.findRegionById(id);
    }

    @GetMapping("/cities/{id}")
    public NovaPostItemDto findCityById(@PathVariable("id") String id) throws JsonProcessingException {
        return novaPostIntegrationService.findCityById(id);
    }

    @GetMapping("/departments/{id}")
    public NovaPostItemDto findDepartmentById(@PathVariable("id") String id) throws JsonProcessingException {
        return novaPostIntegrationService.findDepartmentById(id);
    }
}
