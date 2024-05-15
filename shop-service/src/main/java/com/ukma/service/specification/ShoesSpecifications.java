package com.ukma.service.specification;

import com.ukma.entity.Shoes;
import com.ukma.entity.Shoes_;
import jakarta.persistence.criteria.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ShoesSpecifications {

    private ShoesSpecifications() {
    }

    public static Specification<Shoes> brandSpecification(Long brandId) {
        return (root, query, builder) ->
                builder.equal(root.get(Shoes_.BRAND).get(Shoes_.ID), brandId);
    }

    public static Specification<Shoes> typeSpecification(Long typeId) {
        return (root, query, builder) ->
                builder.equal(root.get(Shoes_.TYPE).get(Shoes_.ID), typeId);
    }

    public static Specification<Shoes> searchSpecification(String search) {
        return (root, query, builder) ->
                builder.like(
                        builder.lower(root.get(Shoes_.NAME)),
                        builder.lower(builder.literal("%" + search.toLowerCase() + "%"))
                );
    }

    public static Specification<Shoes> specificationUnion(List<Specification<Shoes>> specifications) {
        return Specification.allOf(specifications);
    }
}
