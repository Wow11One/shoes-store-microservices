package com.ukma.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "shoes")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class Shoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    Integer price;

    @Column(nullable = false)
    String image;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    Type type;

    @OneToMany(mappedBy = "shoes")
    List<ShoesSize> sizes;

    @OneToMany(mappedBy = "shoes")
    List<ShoesInfo> info;

    @Builder
    public Shoes(String name,
                 Integer price,
                 String image,
                 Instant createdAt,
                 Brand brand,
                 Type type) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.createdAt = createdAt;
        this.brand = brand;
        this.type = type;
    }
}
