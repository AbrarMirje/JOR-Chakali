package com.jor.repository;

import com.jor.entity.UserProductPrices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProductPricesRepository extends JpaRepository<UserProductPrices, Long> {
}
