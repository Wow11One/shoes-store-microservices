package com.ukma.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ukma.dto.order.OrderDetailedDto;
import com.ukma.dto.order.OrderDto;
import com.ukma.dto.order.OrderListDto;
import com.ukma.dto.order.OrderShoesSizeDto;
import com.ukma.dto.shoes.ShoesListItemDto;
import com.ukma.dto.shoes.ShoesSizeDto;
import com.ukma.entity.Order;
import com.ukma.entity.OrderShoesSizes;
import com.ukma.entity.Order_;
import com.ukma.entity.Shoes;
import com.ukma.entity.ShoesSize;
import com.ukma.entity.enums.DeliveryType;
import com.ukma.entity.enums.OrderState;
import com.ukma.entity.enums.PaymentType;
import com.ukma.mapper.OrderMapper;
import com.ukma.mapper.ShoesMapper;
import com.ukma.repository.OrderRepository;
import com.ukma.repository.OrderShoesSizesRepository;
import com.ukma.repository.ShoesSizeRepository;
import com.ukma.service.specification.OrderSpecifications;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderService {

    OrderRepository orderRepository;
    OrderShoesSizesRepository orderShoesSizesRepository;
    ShoesSizeRepository shoesSizeRepository;
    OrderMapper orderMapper;
    ObjectMapper objectMapper;
    ShoesMapper shoesMapper;
    AmqpTemplate amqpTemplate;

    @Transactional
    public OrderDto create(Long userId,
                           String userName,
                           String userSurname,
                           String comment,
                           DeliveryType deliveryType,
                           PaymentType paymentType,
                           String addressJson,
                           String basketJson) throws Exception {
        Order order;
        JsonNode addressNode = objectMapper.readTree(addressJson);

        if (deliveryType.equals(DeliveryType.COURIER)) {
            order = new Order(
                    OrderState.NEW,
                    deliveryType,
                    paymentType,
                    userName,
                    userSurname,
                    comment,
                    addressNode.get("address").textValue(),
                    userId
            );
        } else {
            order = new Order(
                    OrderState.NEW,
                    deliveryType,
                    paymentType,
                    userName,
                    userSurname,
                    comment,
                    addressNode.get("postRegion").textValue(),
                    addressNode.get("postCity").textValue(),
                    addressNode.get("postDepartment").textValue(),
                    userId
            );
        }

        orderRepository.save(order);
        addShoesToOrder(basketJson, order);

        amqpTemplate.convertAndSend("mail.queue","hello vertx");

        return orderMapper.toDto(order);
    }

    @Transactional
    public OrderListDto findAll(Integer limit,
                                Integer page,
                                String search,
                                OrderState state) throws Exception {
        Specification<Order> specification = buildSpecification(search, state);
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Order_.ID));

        Page<Order> orderPage = orderRepository.findAll(specification, pageable);
        List<OrderDto> resultList = orderPage.getContent()
                .stream()
                .map(orderMapper::toDto)
                .toList();

        return new OrderListDto(resultList, (int) orderPage.getTotalElements());
    }

    @Transactional
    public OrderDetailedDto findOne(Long id) throws Exception {
        Order order = orderRepository.findById(id).orElseThrow();

        OrderDetailedDto detailedDto = orderMapper.toDetailedDto(order);
        List<OrderShoesSizeDto> orderShoesSizes = order.getOrderShoesSizesList()
                .stream()
                .map(this::mapToOrderShoesSizeDto)
                .toList();
        detailedDto.setOrderShoesSizes(orderShoesSizes);

        return detailedDto;
    }

    @Transactional
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    private OrderShoesSizeDto mapToOrderShoesSizeDto(OrderShoesSizes orderShoesSize) {
        OrderShoesSizeDto orderShoesSizeDto = new OrderShoesSizeDto();
        orderShoesSizeDto.setId(orderShoesSize.getId());
        orderShoesSizeDto.setAmount(orderShoesSize.getAmount());

        ShoesSize shoesSize = orderShoesSize.getShoesSize();
        ShoesSizeDto shoesSizeDto = new ShoesSizeDto();
        shoesSizeDto.setId(shoesSize.getId());
        shoesSizeDto.setSizeValue(shoesSize.getSizeValue());

        Shoes shoes = shoesSize.getShoes();
        ShoesListItemDto shoesDto = shoesMapper.toListItemDto(shoes);

        shoesSizeDto.setShoesListItemDto(shoesDto);
        orderShoesSizeDto.setShoesSizeDto(shoesSizeDto);

        return orderShoesSizeDto;
    }

    private Specification<Order> buildSpecification(String search, OrderState state) {
        List<Specification<Order>> specifications = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            specifications.add(OrderSpecifications.searchSpecification(search));
        }
        if (state != null) {
            specifications.add(OrderSpecifications.stateSpecification(state));
        }

        return OrderSpecifications.specificationUnion(specifications);
    }


    private void addShoesToOrder(String basket, Order order) throws Exception {
        JsonNode basketNode = objectMapper.readTree(basket);
        basketNode.forEach(node -> {
            ShoesSize shoesSizeReference = shoesSizeRepository
                    .getReferenceById(node.get("id").asLong());
            int amount = node.get("amount").asInt();
            orderShoesSizesRepository.save(new OrderShoesSizes(amount, order, shoesSizeReference));
        });
    }
}
