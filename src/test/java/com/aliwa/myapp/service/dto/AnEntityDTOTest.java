package com.aliwa.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.aliwa.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnEntityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnEntityDTO.class);
        AnEntityDTO anEntityDTO1 = new AnEntityDTO();
        anEntityDTO1.setId(1L);
        AnEntityDTO anEntityDTO2 = new AnEntityDTO();
        assertThat(anEntityDTO1).isNotEqualTo(anEntityDTO2);
        anEntityDTO2.setId(anEntityDTO1.getId());
        assertThat(anEntityDTO1).isEqualTo(anEntityDTO2);
        anEntityDTO2.setId(2L);
        assertThat(anEntityDTO1).isNotEqualTo(anEntityDTO2);
        anEntityDTO1.setId(null);
        assertThat(anEntityDTO1).isNotEqualTo(anEntityDTO2);
    }
}
