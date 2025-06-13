package br.com.cevanotes.service;

import br.com.cevanotes.dto.RotuloDTO;
import br.com.cevanotes.model.Rotulo;
import br.com.cevanotes.repository.RotuloRepository;
import br.com.cevanotes.utils.RotuloFixtures;
import io.javalin.http.NotFoundResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import static br.com.cevanotes.utils.RotuloFixtures.ID_ROTULO;
import static br.com.cevanotes.utils.RotuloFixtures.buildRotulo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        when(repository.insert(rotuloInsert)).thenReturn(1);
        when(repository.findById(1)).thenReturn(Optional.of(rotuloSalvo));

        Rotulo result = service.salvar(dto);

        assertEquals(dto.getNome(), result.getNome());
        assertEquals(dto.getEstilo(), result.getEstilo());
        assertEquals(dto.getTeorAlcoolico(), result.getTeorAlcoolico());
        assertEquals(dto.getCervejaria(), result.getCervejaria());
    }

    @Test
    void testAtualizarRotuloExistente() {
        var idExistente = 2;

        RotuloDTO dto = new RotuloDTO();
        dto.setNome("IPA Puro Malte");
        dto.setEstilo("IPA");
        dto.setTeorAlcoolico(4.5);
        dto.setCervejaria("Alpha");

        Rotulo existente = new Rotulo();
        existente.setId(idExistente);
        existente.setNome("Antigo");
        existente.setEstilo("Antigo");
        existente.setTeorAlcoolico(5.0);
        existente.setCervejaria("Antiga");

        var expectedRotulo = new Rotulo(
                idExistente,
                dto.getNome(),
                dto.getEstilo(),
                dto.getTeorAlcoolico(),
                dto.getCervejaria(),
                LocalDate.now()
        );

        when(repository.findById(idExistente)).thenReturn(Optional.of(existente));
        doNothing().when(repository).update(expectedRotulo);

        var atualizado = service.atualizar(idExistente, dto);

        assertEquals(expectedRotulo, atualizado);
        assertEquals(expectedRotulo.getId(), atualizado.getId());
    }

    public static Stream<Arguments> fornecerCasosParaNaoAtualizacao() {
        return Stream.of(
                Arguments.of("Todos os campos vazios", "", "", 0.0, ""),
                Arguments.of("Todos os campos nulos", null, null, 0.0, null),
                Arguments.of("Campos mistos vazios/nulos", "", null, 0.0, ""),
                Arguments.of("Campos com apenas espaços", "    ", "    ", 0.0, "     ")
        );
    }

    @ParameterizedTest(name = "Cenário {index}: {0}")
    @MethodSource("fornecerCasosParaNaoAtualizacao")
    void testNaoAtualizarCamposQuandoDTOVazio(
            String nomeCenario, String nome, String estilo, double teorAlcoolico, String cervejaria
    ) {

        RotuloDTO dto = new RotuloDTO();
        dto.setNome(nome);
        dto.setEstilo(estilo);
        dto.setTeorAlcoolico(teorAlcoolico);
        dto.setCervejaria(cervejaria);

        Rotulo existente = buildRotulo();

        var expectedRotulo = new Rotulo(
                ID_ROTULO,
                existente.getNome(),
                existente.getEstilo(),
                existente.getTeorAlcoolico(),
                existente.getCervejaria(),
                LocalDate.now()
        );

        when(repository.findById(ID_ROTULO)).thenReturn(Optional.of(existente));
        doNothing().when(repository).update(expectedRotulo);

        var atualizado = service.atualizar(ID_ROTULO, dto);

        assertEquals(expectedRotulo, atualizado);
        assertEquals(expectedRotulo.getId(), atualizado.getId());
    }

    @Test
    void testDarErroQuandoRotuloNaoExistente() {
        RotuloDTO dto = new RotuloDTO();
        dto.setNome("Fake");
        dto.setEstilo("Fake");
        dto.setCervejaria("Fake");
        dto.setTeorAlcoolico(0.0);

        when(repository.findById(999)).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundResponse.class, () ->
                service.atualizar(999, dto));

        assertEquals("Rótulo não encontrado!", exception.getMessage());
    }

    @Test
    void testListarRotulos() {
        List<Rotulo> rotulosEsperados = List.of(
                new Rotulo(1, "IPA", "American IPA", 6.5, "Cervejaria X", LocalDate.now()),
                new Rotulo(2, "Stout", "American stout", 8.6, "Cervejaria Y", LocalDate.now())
        );

        when(repository.findAll()).thenReturn(rotulosEsperados);

        var resultado = service.listar();

        assertEquals(rotulosEsperados, resultado);
        verify(repository, times(1)).findAll();
    }

    @Test
    void testBuscarPorIdExistente() {
        var idExistente = 2;

        Rotulo rotuloEsperado = new Rotulo();
        rotuloEsperado.setId(idExistente);
        rotuloEsperado.setNome("Stout");
        rotuloEsperado.setEstilo("stout");
        rotuloEsperado.setTeorAlcoolico(5.0);
        rotuloEsperado.setCervejaria("Cervejaria Z");

        when(repository.findById(idExistente)).thenReturn(Optional.of(rotuloEsperado));

        var resultado = service.buscarPorId(idExistente);

        assertEquals(rotuloEsperado, resultado);

        verify(repository).findById(anyInt());
    }

    @Test
    void testBuscarPorIdNaoExistente() {
        var idInexistente = 999;

        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundResponse.class, () ->
                service.buscarPorId(idInexistente));

        assertEquals("Rótulo não encontrado!", exception.getMessage());

        verify(repository).findById(anyInt());
    }

    @Test
    void testDeletarRotulos() {
        var expectedId = 2;

        doNothing().when(repository).delete(expectedId);

        service.deletar(expectedId);

        verify(repository, times(1)).delete(anyInt());
    }
}