package br.com.cevanotes.utils;

import br.com.cevanotes.dto.RotuloDTO;
import br.com.cevanotes.model.Rotulo;

public class RotuloFixtures {

    public static final int ID_ROTULO = 1;
    public static final String NOME_ROTULO = "Heineken";
    public static final String NOME_ROTULO_DTO = "Heineken DTO";
    public static final String ESTILO_ROTULO = "Larger";
    public static final String ESTILO_ROTULO_DTO = "Larger DTO";
    public static final double TEOR_ALCOOLICO_ROTULO = 5.0;
    public static final double TEOR_ALCOOLICO_ROTULO_DTO = 8.0;
    public static final String CERVEJARIA_ROTULO = "Heinenken";
    public static final String CERVEJARIA_ROTULO_DTO = "Heinenken DTO";

    public static Rotulo buildRotulo() {
        Rotulo rotulo = new Rotulo();
        rotulo.setId(ID_ROTULO);
        rotulo.setNome(NOME_ROTULO);
        rotulo.setEstilo(ESTILO_ROTULO);
        rotulo.setTeorAlcoolico(TEOR_ALCOOLICO_ROTULO);
        rotulo.setCervejaria(CERVEJARIA_ROTULO);
        return rotulo;
    }

    public static RotuloDTO buildRotuloDTO() {
        RotuloDTO dto = new RotuloDTO();
        dto.setNome(NOME_ROTULO_DTO);
        dto.setEstilo(ESTILO_ROTULO_DTO);
        dto.setTeorAlcoolico(TEOR_ALCOOLICO_ROTULO_DTO);
        dto.setCervejaria(CERVEJARIA_ROTULO_DTO);
        return dto;
    }
}
