package com.aliwa.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnEntityMapperTest {

    private AnEntityMapper anEntityMapper;

    @BeforeEach
    public void setUp() {
        anEntityMapper = new AnEntityMapperImpl();
    }
}
