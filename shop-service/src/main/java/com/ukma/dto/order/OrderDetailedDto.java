package com.ukma.dto.order;

import com.ukma.entity.OrderShoesSizes;
import com.ukma.entity.enums.DeliveryType;
import com.ukma.entity.enums.OrderState;
import com.ukma.entity.enums.PaymentType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class OrderDetailedDto extends OrderDto {

    List<OrderShoesSizeDto> orderShoesSizes;
    String address;
    String postRegion;
    String postCity;
    String postDepartment;

    public OrderDetailedDto(Long id,
                            OrderState orderState,
                            DeliveryType deliveryType,
                            PaymentType paymentType,
                            String username,
                            String userSurname,
                            Long userId,
                            List<OrderShoesSizeDto> orderShoesSizes,
                            Instant createdAt,
                            String address,
                            String postRegion,
                            String postCity,
                            String postDepartment) {
        super(id, orderState, deliveryType, paymentType, username, userSurname, userId, createdAt);
        this.orderShoesSizes = orderShoesSizes;
        this.postRegion = postRegion;
        this.postCity = postCity;
        this.postDepartment = postDepartment;
        this.address = address;
    }
}
