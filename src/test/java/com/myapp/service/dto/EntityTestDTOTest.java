package com.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntityTestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntityTestDTO.class);
        EntityTestDTO entityTestDTO1 = new EntityTestDTO();
        entityTestDTO1.setId(1L);
        EntityTestDTO entityTestDTO2 = new EntityTestDTO();
        assertThat(entityTestDTO1).isNotEqualTo(entityTestDTO2);
        entityTestDTO2.setId(entityTestDTO1.getId());
        assertThat(entityTestDTO1).isEqualTo(entityTestDTO2);
        entityTestDTO2.setId(2L);
        assertThat(entityTestDTO1).isNotEqualTo(entityTestDTO2);
        entityTestDTO1.setId(null);
        assertThat(entityTestDTO1).isNotEqualTo(entityTestDTO2);
    }
}
