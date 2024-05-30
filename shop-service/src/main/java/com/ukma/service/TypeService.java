package com.ukma.service;

import com.ukma.dto.type.TypeDto;
import com.ukma.dto.type.TypeListDto;
import com.ukma.entity.Type;
import com.ukma.mapper.TypeMapper;
import com.ukma.repository.TypeRepository;
import com.ukma.service.specification.TypeSpecifications;
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
public class TypeService {

    TypeRepository typeRepository;
    TypeMapper typeMapper;

    public TypeDto create(TypeDto typeDto) {
        Type type = new Type(typeDto.getName());
        typeRepository.save(type);

        return typeMapper.toDto(type);
    }

    public TypeListDto findAll(Integer page, Integer limit, String search) {
        Specification<Type> specification = buildSpecification(search);
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("id"));

        Page<Type> TypePage = typeRepository.findAll(specification, pageable);
        List<TypeDto> TypeDtoList = TypePage.getContent()
                .stream()
                .map(typeMapper::toDto)
                .toList();

        return new TypeListDto(TypeDtoList, (int) TypePage.getTotalElements());
    }

    public TypeDto update(Long id, TypeDto typeDto) {
        Type type = typeRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("type with such id does not exist")
        );

        type.setName(typeDto.getName());
        typeRepository.save(type);

        return typeMapper.toDto(type);
    }

    @Transactional
    public void deleteById(Long id) {
        typeRepository.deleteById(id);
    }

    private Specification<Type> buildSpecification(String search) {
        List<Specification<Type>> specifications = new ArrayList<>();
        if (search != null && !search.trim().isEmpty()) {
            specifications.add(TypeSpecifications.searchSpecification(search));
        }

        return TypeSpecifications.specificationUnion(specifications);
    }
}
