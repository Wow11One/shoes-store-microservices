package com.ukma.repository;


import com.ukma.entity.ShoesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoesInfoRepository extends JpaRepository<ShoesInfo, Long> {

    void deleteAllByShoesId(Long shoesId);
}
