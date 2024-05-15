package com.ukma.dto.order;

import com.ukma.entity.OrderShoesSizes;
import com.ukma.entity.enums.DeliveryType;
import com.ukma.entity.enums.OrderState;
import com.ukma.entity.enums.PaymentType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class OrderDto {

    Long id;
    OrderState state;
    DeliveryType deliveryType;
    PaymentType paymentType;
    String username;
    String userSurname;
    Long userId;
    Instant createdAt;

    public OrderDto(Long id,
                    OrderState orderState,
                    DeliveryType deliveryType,
                    PaymentType paymentType,
                    String username,
                    String userSurname,
                    Long userId,
                    Instant createdAt) {
        this.id = id;
        this.state = orderState;
        this.deliveryType = deliveryType;
        this.paymentType = paymentType;
        this.username = username;
        this.userSurname = userSurname;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
