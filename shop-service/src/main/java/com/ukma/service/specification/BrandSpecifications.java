package com.ukma.service.specification;

import com.ukma.entity.Brand;
import com.ukma.entity.Brand_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class BrandSpecifications {

    private BrandSpecifications() {
    }

    public static Specification<Brand> searchSpecification(String search) {
        return (root, query, builder) ->
                builder.like(
                        builder.lower(root.get(Brand_.NAME)),
                        builder.lower(builder.literal("%" + search.toLowerCase() + "%"))
                );
    }

    public static Specification<Brand> specificationUnion(List<Specification<Brand>> specifications) {
        return Specification.allOf(specifications);
    }
}
