package com.ukma.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "order_shoes_sizes")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class OrderShoesSizes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoes_size_id")
    ShoesSize shoesSize;

    public OrderShoesSizes(Integer amount, Order order, ShoesSize shoesSize) {
        this.amount = amount;
        this.order = order;
        this.shoesSize = shoesSize;
    }
}
