package com.ukma.repository;

import com.ukma.entity.ShoesSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoesSizeRepository extends JpaRepository<ShoesSize, Long> {

    void deleteAllByShoesId(Long shoesId);
}
