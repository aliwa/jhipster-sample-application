package com.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntityTestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntityTest.class);
        EntityTest entityTest1 = new EntityTest();
        entityTest1.setId(1L);
        EntityTest entityTest2 = new EntityTest();
        entityTest2.setId(entityTest1.getId());
        assertThat(entityTest1).isEqualTo(entityTest2);
        entityTest2.setId(2L);
        assertThat(entityTest1).isNotEqualTo(entityTest2);
        entityTest1.setId(null);
        assertThat(entityTest1).isNotEqualTo(entityTest2);
    }
}
