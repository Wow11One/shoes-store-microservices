package com.ukma.service.specification;

import com.ukma.entity.Order;
import com.ukma.entity.Order_;
import com.ukma.entity.enums.OrderState;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class OrderSpecifications {

    private OrderSpecifications() {
    }

    public static Specification<Order> searchSpecification(String search) {
        return (root, query, builder) ->
                builder.like(
                        builder.lower(root.get(Order_.USERNAME)),
                        builder.lower(builder.literal("%" + search.toLowerCase() + "%"))
                );
    }

    public static Specification<Order> stateSpecification(OrderState state) {
        return (root, query, builder) ->
                builder.equal(root.get(Order_.ORDER_STATE), state.toString());
    }

    public static Specification<Order> specificationUnion(List<Specification<Order>> specifications) {
        return Specification.allOf(specifications);
    }
}
