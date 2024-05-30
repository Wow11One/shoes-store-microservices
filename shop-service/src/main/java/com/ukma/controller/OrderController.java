package com.ukma.controller;

import com.ukma.dto.order.OrderDetailedDto;
import com.ukma.dto.order.OrderDto;
import com.ukma.dto.order.OrderListDto;
import com.ukma.entity.enums.DeliveryType;
import com.ukma.entity.enums.OrderState;
import com.ukma.entity.enums.PaymentType;
import com.ukma.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestParam("userName") String userName,
                           @RequestParam("userSurname") String userSurname,
                           @RequestParam(value = "comment", required = false)
                           String comment,
                           @RequestParam("deliveryType") DeliveryType deliveryType,
                           @RequestParam("paymentType") PaymentType paymentType,
                           @RequestParam("address") String addressJson,
                           @RequestParam("basket") String basketJson) throws Exception {
        return orderService.create(
                userName,
                userSurname,
                comment,
                deliveryType,
                paymentType,
                addressJson,
                basketJson
        );
    }

    @GetMapping
    public OrderListDto findAll(@RequestParam(value = "limit", defaultValue = "" + Integer.MAX_VALUE)
                                Integer limit,
                                @RequestParam(value = "page", defaultValue = "1")
                                Integer page,
                                @RequestParam("search")
                                String search,
                                @RequestParam(value = "state", required = false)
                                OrderState state) throws Exception {
        return orderService.findAll(limit, page, search, state);
    }

    @GetMapping("/{id}")
    public OrderDetailedDto findOne(@PathVariable("id") Long id) throws Exception {
        return orderService.findOne(id);
    }
}
