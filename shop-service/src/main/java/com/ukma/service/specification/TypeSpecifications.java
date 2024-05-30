package com.ukma.service.specification;

import com.ukma.entity.Type;
import com.ukma.entity.Type_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class TypeSpecifications {

    private TypeSpecifications() {
    }

    public static Specification<Type> searchSpecification(String search) {
        return (root, query, builder) ->
                builder.like(
                        builder.lower(root.get(Type_.NAME)),
                        builder.lower(builder.literal("%" + search.toLowerCase() + "%"))
                );
    }

    public static Specification<Type> specificationUnion(List<Specification<Type>> specifications) {
        return Specification.allOf(specifications);
    }
}
