package br.com.cevanotes.service;

import br.com.cevanotes.dto.RotuloDTO;
import br.com.cevanotes.model.Rotulo;
import br.com.cevanotes.repository.RotuloRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RotuloServiceTest {

    @Mock
    RotuloRepository repository; // será injetado no service

    @InjectMocks
    RotuloService service; // terá o mock injetado automaticamente

    @Test
    void testSalvarRotulo() {
        RotuloDTO dto = new RotuloDTO();
        dto.setNome("IPA Puro Malte");
        dto.setEstilo("IPA");
        dto.setTeorAlcoolico(4.5);
        dto.setCervejaria("Alpha");

        Rotulo rotuloSalvo = new Rotulo();
        rotuloSalvo.setId(1);
        rotuloSalvo.setNome(dto.getNome());
        rotuloSalvo.setEstilo(dto.getEstilo());
        rotuloSalvo.setTeorAlcoolico(dto.getTeorAlcoolico());
        rotuloSalvo.setCervejaria(dto.getCervejaria());
        rotuloSalvo.setDataCadastro(LocalDate.now());

        Rotulo rotuloInsert = new Rotulo();
        rotuloInsert.setNome(dto.getNome());
        rotuloInsert.setEstilo(dto.getEstilo());
        rotuloInsert.setTeorAlcoolico(dto.getTeorAlcoolico());
        rotuloInsert.setCervejaria(dto.getCervejaria());
        rotuloInsert.setDataCadastro(LocalDate.now());

        Mockito.when(repository.insert(rotuloInsert)).thenReturn(1);
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(rotuloSalvo));

        Rotulo result = service.salvar(dto);

        assertEquals(dto.getNome(), result.getNome());
        assertEquals(dto.getEstilo(), result.getEstilo());
        assertEquals(dto.getTeorAlcoolico(), result.getTeorAlcoolico());
        assertEquals(dto.getCervejaria(), result.getCervejaria());
    }
}