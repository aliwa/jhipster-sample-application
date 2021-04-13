package com.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntityTestMapperTest {

    private EntityTestMapper entityTestMapper;

    @BeforeEach
    public void setUp() {
        entityTestMapper = new EntityTestMapperImpl();
    }
}
