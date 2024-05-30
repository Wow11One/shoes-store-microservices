package com.ukma.repository;

import com.ukma.entity.OrderShoesSizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderShoesSizesRepository extends JpaRepository<OrderShoesSizes, Long> {
}
