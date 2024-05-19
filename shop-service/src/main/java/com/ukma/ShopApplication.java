package com.ukma;

import com.ukma.entity.Brand;
import com.ukma.entity.Shoes;
import com.ukma.entity.ShoesSize;
import com.ukma.entity.Type;
import com.ukma.repository.BrandRepository;
import com.ukma.repository.ShoesRepository;
import com.ukma.repository.ShoesSizeRepository;
import com.ukma.repository.TypeRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.time.Instant;


@SpringBootApplication
@EnableDiscoveryClient
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopApplication implements CommandLineRunner {
    TypeRepository typeRepository;
    BrandRepository brandRepository;
    ShoesRepository shoesRepository;
    ShoesSizeRepository shoesSizeRepository;

    @Autowired
    public ShopApplication(TypeRepository typeRepository,
                           BrandRepository brandRepository,
                           ShoesRepository shoesRepository,
                           ShoesSizeRepository shoesSizeRepository) {
        this.typeRepository = typeRepository;
        this.brandRepository = brandRepository;
        this.shoesRepository = shoesRepository;
        this.shoesSizeRepository = shoesSizeRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        Type type = new Type("Type");
        Brand brand = new Brand("Brand");

        typeRepository.save(type);
        brandRepository.save(brand);

        Shoes shoes = new Shoes(
                "shoes",
                3243,
                "https://res.cloudinary.com/dbkgbcqcf/image/upload/v1/shoes/bskxgmjpiabv3q51ujls",
                Instant.now(),
                brand,
                type
        );

        ShoesSize shoesSize = new ShoesSize(37.5, shoes);
        shoesRepository.save(shoes);
        shoesSizeRepository.save(shoesSize);
    }
}