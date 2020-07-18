package com.gestion.etudiant.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gestion.etudiant.web.rest.TestUtil;

public class ExamenTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Examen.class);
        Examen examen1 = new Examen();
        examen1.setId(1L);
        Examen examen2 = new Examen();
        examen2.setId(examen1.getId());
        assertThat(examen1).isEqualTo(examen2);
        examen2.setId(2L);
        assertThat(examen1).isNotEqualTo(examen2);
        examen1.setId(null);
        assertThat(examen1).isNotEqualTo(examen2);
    }
}
