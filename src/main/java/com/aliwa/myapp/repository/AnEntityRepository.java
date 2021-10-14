package com.aliwa.myapp.repository;

import com.aliwa.myapp.domain.AnEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnEntityRepository extends JpaRepository<AnEntity, Long> {}
