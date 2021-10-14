package com.aliwa.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.aliwa.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnEntity.class);
        AnEntity anEntity1 = new AnEntity();
        anEntity1.setId(1L);
        AnEntity anEntity2 = new AnEntity();
        anEntity2.setId(anEntity1.getId());
        assertThat(anEntity1).isEqualTo(anEntity2);
        anEntity2.setId(2L);
        assertThat(anEntity1).isNotEqualTo(anEntity2);
        anEntity1.setId(null);
        assertThat(anEntity1).isNotEqualTo(anEntity2);
    }
}
