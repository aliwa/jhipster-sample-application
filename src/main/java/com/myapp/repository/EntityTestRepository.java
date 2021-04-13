package com.myapp.repository;

import com.myapp.domain.EntityTest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EntityTest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntityTestRepository extends JpaRepository<EntityTest, Long> {}
