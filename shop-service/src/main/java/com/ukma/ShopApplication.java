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
        Type typeCasual = new Type("Casual");
        Type typeClassic = new Type("Classic");
        Type typeSoccer = new Type("Soccer");

        Brand brandAdidas = new Brand("Brand");
        Brand brandUmbro = new Brand("Umbro");
        Brand brandJordan = new Brand("Jordan");

        typeRepository.save(typeCasual);
        typeRepository.save(typeClassic);
        typeRepository.save(typeSoccer);

        brandRepository.save(brandAdidas);
        brandRepository.save(brandUmbro);
        brandRepository.save(brandJordan);

        Shoes hyperturfBlack = new Shoes(
                "Hyperturf black",
                7500,
                "https://res.cloudinary.com/dbkgbcqcf/image/upload/v1715842008/shoes/f3xr5grm5f7bu1xbjhuq.webp",
                Instant.now(),
                brandAdidas,
                typeClassic
        );
        Shoes hyperturfWhite = new Shoes(
                "Hyperturf white",
                6500,
                "https://res.cloudinary.com/dbkgbcqcf/image/upload/v1715978889/shoes/sohbayigkbi6q369q7ak.webp",
                Instant.now(),
                brandAdidas,
                typeCasual
        );
        Shoes umbroWhite = new Shoes(
                "Umbro terra white-green",
                3400,
                "https://res.cloudinary.com/dbkgbcqcf/image/upload/v1716147897/lotto_lltvbq.webp",
                Instant.now(),
                brandUmbro,
                typeSoccer
        );
        Shoes umbroBlue = new Shoes(
                "Umbro terra blue-white",
                2900,
                "https://res.cloudinary.com/dbkgbcqcf/image/upload/v1715841816/shoes/tyz2akpz8o4wyqyr0ck6.webp",
                Instant.now(),
                brandUmbro,
                typeSoccer
        );
        Shoes jordanBlue = new Shoes(
                "Air jordan mid sky blue white",
                8900,
                "https://res.cloudinary.com/dbkgbcqcf/image/upload/v1716148310/air_jordan_white_mid_okangk.webp",
                Instant.now(),
                brandJordan,
                typeClassic
        );
        Shoes jordanBrown = new Shoes(
                "Air jordan mid 1 splatter brown",
                10500,
                "https://res.cloudinary.com/dbkgbcqcf/image/upload/v1716148324/AIR_JORDAN_1_MID_SE_BRUSHSTROKE_PAINT_SPLATTER_BROWN_cc87rv.webp",
                Instant.now(),
                brandJordan,
                typeCasual
        );
        Shoes jordanPink = new Shoes(
                "Air jordan high pink",
                12000,
                "https://res.cloudinary.com/dbkgbcqcf/image/upload/v1715843793/shoes/r7h4bh2nuyi4gpp9ovk8.webp",
                Instant.now(),
                brandJordan,
                typeCasual
        );

        ShoesSize shoesSizeFirst = new ShoesSize(37.5, hyperturfBlack);
        ShoesSize shoesSizeSecond = new ShoesSize(38., hyperturfBlack);
        ShoesSize shoesSizeThird = new ShoesSize(44., hyperturfBlack);
        ShoesSize shoesSizeFourth = new ShoesSize(40., hyperturfWhite);
        ShoesSize shoesSizeFifth = new ShoesSize(41., hyperturfWhite);
        ShoesSize shoesSizeSixth = new ShoesSize(44., umbroWhite);

        shoesRepository.save(hyperturfBlack);
        shoesRepository.save(hyperturfWhite);
        shoesRepository.save(umbroWhite);
        shoesRepository.save(umbroBlue);
        shoesRepository.save(jordanBlue);
        shoesRepository.save(jordanBrown);
        shoesRepository.save(jordanPink);

        shoesSizeRepository.save(shoesSizeFirst);
        shoesSizeRepository.save(shoesSizeSecond);
        shoesSizeRepository.save(shoesSizeThird);
        shoesSizeRepository.save(shoesSizeFourth);
        shoesSizeRepository.save(shoesSizeFifth);
        shoesSizeRepository.save(shoesSizeSixth);

    }
}