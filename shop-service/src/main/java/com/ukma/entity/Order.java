package com.ukma.entity;

import com.ukma.entity.enums.DeliveryType;
import com.ukma.entity.enums.OrderState;
import com.ukma.entity.enums.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, name = "state")
    @Enumerated(value = EnumType.STRING)
    OrderState orderState;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    DeliveryType deliveryType;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    PaymentType paymentType;

    @Column(nullable = false, name = "user_name")
    String username;

    @Column(nullable = false, name = "user_surname")
    String userSurname;

    @Column
    String comment;

    @Column
    String address;

    @Column
    String postRegion;

    @Column
    String postCity;

    @Column
    String postDepartment;

    @Column(nullable = false)
    Long userId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant createdAt;

    @OneToMany(mappedBy = "order")
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<OrderShoesSizes> orderShoesSizesList;

    public Order(OrderState orderState,
                 DeliveryType deliveryType,
                 PaymentType paymentType,
                 String username,
                 String userSurname,
                 String comment,
                 String postRegion,
                 String postCity,
                 String postDepartment,
                 Long userId) {
        this.orderState = orderState;
        this.deliveryType = deliveryType;
        this.paymentType = paymentType;
        this.username = username;
        this.userSurname = userSurname;
        this.comment = comment;
        this.postRegion = postRegion;
        this.postCity = postCity;
        this.postDepartment = postDepartment;
        this.userId = userId;
    }

    public Order(OrderState orderState,
                 DeliveryType deliveryType,
                 PaymentType paymentType,
                 String username,
                 String userSurname,
                 String comment,
                 String address,
                 Long userId) {
        this.orderState = orderState;
        this.deliveryType = deliveryType;
        this.paymentType = paymentType;
        this.username = username;
        this.userSurname = userSurname;
        this.comment = comment;
        this.address = address;
        this.userId = userId;
    }
}
