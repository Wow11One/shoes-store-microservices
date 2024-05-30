package com.ukma.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ukma.dto.nova.post.NovaPostItemDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NovaPostIntegrationService {

    RestTemplate restTemplate;
    ObjectMapper objectMapper;

    @Value("${nova.post.url}")
    String novaPostApiUrl;

    public NovaPostIntegrationService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<NovaPostItemDto> findAllRegions() throws JsonProcessingException {
        String requestBody = """
                {
                    "modelName": "Address",
                    "calledMethod": "getAreas"
                }
                """;
        List<NovaPostItemDto> regions = fetchData(requestBody).stream()
                .filter(region -> !region.getName().equalsIgnoreCase("АРК"))
                .toList();
        regions.forEach(region -> region.setName(region.getName() + " область"));

        return regions;
    }

    public List<NovaPostItemDto> findAllCitiesByRegion(String regionId) throws JsonProcessingException {
        String requestBody = """
                {
                    "modelName": "Address",
                    "calledMethod": "getCities",
                    "methodProperties": {
                        "AreaRef": "%s"
                    }
                }
                """.formatted(regionId);

        return fetchData(requestBody);
    }

    public List<NovaPostItemDto> findAllDepartmentsByCity(String cityId) throws JsonProcessingException {
        String requestBody = """
                {
                    "modelName": "Address",
                    "calledMethod": "getWarehouses",
                    "methodProperties": {
                        "CityRef": "%s"
                    }
                }
                """.formatted(cityId);

        return fetchData(requestBody);
    }

    public NovaPostItemDto findRegionById(String id) throws JsonProcessingException {
        String requestBody = """
                {
                    "modelName": "Address",
                    "calledMethod": "getAreas",
                    "methodProperties": {
                        "Ref": "%s"
                    }
                }
                """.formatted(id);
        NovaPostItemDto resultRegion = fetchData(requestBody).get(0);
        resultRegion.setName(resultRegion.getName() + " область");

        return resultRegion;
    }

    public NovaPostItemDto findCityById(String id) throws JsonProcessingException {
        String requestBody = """
                {
                    "modelName": "Address",
                    "calledMethod": "getCities",
                    "methodProperties": {
                                    "Ref": "%s"
                                }
                }
                """.formatted(id);

        return fetchData(requestBody).get(0);
    }

    public NovaPostItemDto findDepartmentById(String id) throws JsonProcessingException {
        String requestBody = """
                {
                    "modelName": "Address",
                    "calledMethod": "getWarehouses",
                    "methodProperties": {
                                    "Ref": "%s"
                                }
                }
                """.formatted(id);

        return fetchData(requestBody).get(0);
    }

    private List<NovaPostItemDto> fetchData(String requestBody) throws JsonProcessingException {
        String responseBody = restTemplate.postForObject(novaPostApiUrl, requestBody, String.class);
        JsonNode responseData = objectMapper.readTree(responseBody).get("data");

        return objectMapper.readValue(responseData.toString(), new TypeReference<>() {
        });
    }
}
