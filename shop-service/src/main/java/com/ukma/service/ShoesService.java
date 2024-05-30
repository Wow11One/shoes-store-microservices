package com.ukma.service;

import com.cloudinary.Cloudinary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ukma.dto.shoes.ShoesInfoDto;
import com.ukma.dto.shoes.ShoesListDto;
import com.ukma.dto.shoes.ShoesListItemDto;
import com.ukma.dto.shoes.ShoesPageDto;
import com.ukma.entity.Brand;
import com.ukma.entity.Shoes;
import com.ukma.entity.ShoesInfo;
import com.ukma.entity.ShoesSize;
import com.ukma.entity.Shoes_;
import com.ukma.entity.Type;
import com.ukma.mapper.ShoesMapper;
import com.ukma.repository.BrandRepository;
import com.ukma.repository.ShoesInfoRepository;
import com.ukma.repository.ShoesRepository;
import com.ukma.repository.ShoesSizeRepository;
import com.ukma.repository.TypeRepository;
import com.ukma.service.specification.ShoesSpecifications;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ShoesService {

    final ShoesRepository shoesRepository;
    final ShoesInfoRepository shoesInfoRepository;
    final ShoesSizeRepository shoesSizeRepository;
    final BrandRepository brandRepository;
    final TypeRepository typeRepository;
    final ObjectMapper objectMapper;
    final ShoesMapper shoesMapper;
    final CloudinaryService cloudinaryService;

    @Value("${upload.path}")
    String uploadPath;

    public ShoesService(ShoesRepository shoesRepository,
                        ShoesInfoRepository shoesInfoRepository,
                        ShoesSizeRepository shoesSizeRepository,
                        BrandRepository brandRepository,
                        TypeRepository typeRepository,
                        ObjectMapper objectMapper,
                        ShoesMapper shoesMapper,
                        CloudinaryService cloudinaryService) {
        this.shoesRepository = shoesRepository;
        this.shoesInfoRepository = shoesInfoRepository;
        this.shoesSizeRepository = shoesSizeRepository;
        this.brandRepository = brandRepository;
        this.typeRepository = typeRepository;
        this.objectMapper = objectMapper;
        this.shoesMapper = shoesMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @Transactional
    public ShoesListItemDto create(String name,
                                   Integer price,
                                   Long brandId,
                                   Long typeId,
                                   String infoJsonArray,
                                   String sizesJsonArray,
                                   MultipartFile file) throws Exception {
        Type type = typeRepository.findById(typeId)
                .orElseThrow(() -> new NoSuchElementException("type with such id does not exist"));
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new NoSuchElementException("brand with such id does not exist"));

        String imageName = uploadImage(file);
        Shoes shoes = Shoes.builder()
                .name(name)
                .price(price)
                .brand(brand)
                .type(type)
                .image(imageName)
                .build();

        shoesRepository.save(shoes);
        createInfo(infoJsonArray, shoes);
        createSizes(sizesJsonArray, shoes);


        return shoesMapper.toListItemDto(shoes);
    }

    @Transactional(readOnly = true)
    public ShoesListDto findAll(Long brandId,
                                Long typeId,
                                Integer page,
                                Integer limit,
                                String search,
                                String order) {
        Specification<Shoes> specification = buildSpecification(brandId, typeId, search);
        Pageable pageable = PageRequest.of(page - 1, limit, buildSort(order));

        Page<Shoes> shoesPage = shoesRepository.findAll(specification, pageable);
        List<ShoesListItemDto> responseList = shoesPage.stream()
                .map(shoesMapper::toListItemDto)
                .toList();

        return new ShoesListDto(responseList, (int) shoesPage.getTotalElements());
    }

    private Specification<Shoes> buildSpecification(Long brandId,
                                                    Long typeId,
                                                    String search) {

        List<Specification<Shoes>> specifications = new ArrayList<>();

        if (brandId != null) {
            specifications.add(ShoesSpecifications.brandSpecification(brandId));
        }
        if (typeId != null) {
            specifications.add(ShoesSpecifications.typeSpecification(typeId));
        }
        if (search != null && !search.trim().isEmpty()) {
            specifications.add(ShoesSpecifications.searchSpecification(search));
        }

        return ShoesSpecifications.specificationUnion(specifications);
    }

    @Transactional
    public ShoesPageDto findOne(Long id) {
        Shoes shoes = shoesRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("no shoes with such id")
        );

        return shoesMapper.toPageDto(shoes);
    }

    private Sort buildSort(String sort) {
        Sort defaultSort = Sort.by(Shoes_.CREATED_AT).ascending();
        if (sort == null || sort.trim().isEmpty()) {
            return defaultSort;
        }

        String[] sortTokens = sort.split(" ");
        if (sortTokens.length != 2) {
            return defaultSort;
        }

        return sortTokens[1].equalsIgnoreCase("asc")
                ? Sort.by(sortTokens[0]).ascending()
                : Sort.by(sortTokens[0]).descending();
    }


    @Transactional
    public ShoesListItemDto update(Long id,
                                   String name,
                                   Integer price,
                                   Long brandId,
                                   Long typeId,
                                   String infoJsonArray,
                                   String sizesJsonArray,
                                   MultipartFile file) throws Exception {
        Shoes shoes = shoesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("shoes with such id does not exist"));
        Type type = typeRepository.findById(typeId)
                .orElseThrow(() -> new NoSuchElementException("type with such id does not exist"));
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new NoSuchElementException("brand with such id does not exist"));


        if (!(file == null || file.isEmpty())) {
            String newImageUrl = cloudinaryService.uploadFile(file);
            shoes.setImage(newImageUrl);
        }

        shoesInfoRepository.deleteAllByShoesId(id);
        createInfo(infoJsonArray, shoes);

        shoesSizeRepository.deleteAllByShoesId(id);
        createSizes(sizesJsonArray, shoes);

        shoes.setName(name);
        shoes.setPrice(price);
        shoes.setType(type);
        shoes.setBrand(brand);
        shoesRepository.save(shoes);

        return shoesMapper.toListItemDto(shoes);
    }

    @Transactional
    public void deleteById(Long id) {
        shoesRepository.deleteById(id);
    }

    private void createInfo(String infoJson, Shoes shoes) throws JsonProcessingException {
        List<ShoesInfo> info = objectMapper.readValue(infoJson, new TypeReference<>() {
        });
        info.forEach(item ->
                shoesInfoRepository.save(new ShoesInfo(item.getTitle(), item.getDescription(), shoes)));
    }

    private void createSizes(String sizesJson, Shoes shoes) throws JsonProcessingException {
        List<Double> sizes = objectMapper.readValue(sizesJson, new TypeReference<>() {
        });
        sizes.forEach(size ->
                shoesSizeRepository.save(new ShoesSize(size, shoes)));
    }

    private String uploadImage(MultipartFile file) throws Exception {
        String url = Optional.ofNullable(cloudinaryService.uploadFile(file)).orElseThrow(
                () -> new NoSuchElementException("no such image")
        );

        return url;
    }
}
