package br.com.cevanotes.controller;

import br.com.cevanotes.dto.RotuloDTO;
import br.com.cevanotes.model.Rotulo;
import br.com.cevanotes.service.RotuloService;
import br.com.cevanotes.utils.RotuloFixtures;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.testtools.JavalinTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static br.com.cevanotes.utils.RotuloFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RotuloControllerTest {

    @Mock
    private RotuloService rotuloService;

    @InjectMocks
    private RotuloController rotuloController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void deveListarTodosOsRotulos() {
        Rotulo rotulo1 = criarRotuloMock(1, "Heineken", "Larger", 5.0, "Heinenken");
        Rotulo rotulo2 = criarRotuloMock(2, "Stella", "Pilsner", 4.0, "Stella");

        List<Rotulo> rotulos = List.of(rotulo1, rotulo2);
        when(rotuloService.listar()).thenReturn(rotulos);

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            var response = client.get("/rotulos");
            assertEquals(200, response.code());

            assertNotNull(response.body());
            List<Rotulo> responseRotulos = objectMapper.readValue(
                    response.body().string(),
                    new TypeReference<List<Rotulo>>() {
                    }
            );

            assertEquals(2, responseRotulos.size());
            assertEquals("Heineken", responseRotulos.get(0).getNome());
            assertEquals("Stella", responseRotulos.get(1).getNome());
        });

        verify(rotuloService).listar();
    }

    @NotNull
    private static Rotulo criarRotuloMock(int id, String nome, String estilo, double teor, String cervejaria) {
        Rotulo rotulo = new Rotulo();
        rotulo.setId(id);
        rotulo.setNome(nome);
        rotulo.setEstilo(estilo);
        rotulo.setTeorAlcoolico(teor);
        rotulo.setCervejaria(cervejaria);
        return rotulo;
    }

    @Test
    void deveRetornarArrayVazioQuandoNaoHouverRotulos() {

        when(rotuloService.listar()).thenReturn(List.of());

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            var response = client.get("/rotulos");
            assertEquals(200, response.code());

            assertNotNull(response.body());
            var responseRotulos = objectMapper.readValue(
                    response.body().string(),
                    new TypeReference<List<Rotulo>>() {
                    }
            );

            assertTrue(responseRotulos.isEmpty());
        });
    }

    @NotNull
    private Javalin criarAppComRotas() {
        Javalin app = Javalin.create();
        rotuloController.registrarRotas(app);
        return app;
    }

    @Test
    void deveBuscarRotuloPorId() {
        Rotulo rotulo = buildRotulo();

        when(rotuloService.buscarPorId(ID_ROTULO)).thenReturn(rotulo);

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            var response = client.get("/rotulos/" + ID_ROTULO);
            assertEquals(200, response.code());

            assert response.body() != null;
            Rotulo responseRotulo = objectMapper.readValue(
                    response.body().string(),
                    Rotulo.class
            );

            assertEquals(ID_ROTULO, responseRotulo.getId());
            assertEquals(NOME_ROTULO, responseRotulo.getNome());
            assertEquals(ESTILO_ROTULO, responseRotulo.getEstilo());
            assertEquals(TEOR_ALCOOLICO_ROTULO, responseRotulo.getTeorAlcoolico());
        });

        verify(rotuloService).buscarPorId(ID_ROTULO);
    }

    @Test
    void deveRetornar404AoBuscarRotuloInexistente() {
        when(rotuloService.buscarPorId(ID_ROTULO)).thenThrow(new NotFoundResponse("Rótulo não encontrado"));

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            var response = client.get("/rotulos/" + ID_ROTULO);
            assertEquals(404, response.code());
        });

        verify(rotuloService).buscarPorId(ID_ROTULO);
    }

    @Test
    void deveRetornar400ComIdInvalido() {
        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            var response = client.get("/rotulos/dsdas");
            assertEquals(400, response.code());
            assert response.body() != null;
            assertTrue(response.body().string().contains("ID inválido. Use um numero inteiro!"));
        });

        verifyNoInteractions(rotuloService);
    }

    @NotNull
    private static RotuloDTO criarRotuloDTO(String nome, String estilo, double teor, String cervejaria) {
        RotuloDTO dto = new RotuloDTO();
        dto.setNome(nome);
        dto.setEstilo(estilo);
        dto.setTeorAlcoolico(teor);
        dto.setCervejaria(cervejaria);
        return dto;
    }

    @Test
    void deveCriarNovoRotulo() {
        var dto = criarRotuloDTO("Corona", "Lager", 4.5, "Cervejaria");
        var rotuloSalvo = buildRotulo();

        when(rotuloService.salvar(dto)).thenReturn(rotuloSalvo);

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            String jsonBody = objectMapper.writeValueAsString(dto);
            var response = client.post("/rotulos", jsonBody);

            assertEquals(201, response.code());

            var responseRotulo = objectMapper.readValue(
                    response.body().string(),
                    Rotulo.class
            );

            assertEquals(ID_ROTULO, responseRotulo.getId());
            assertEquals(NOME_ROTULO, responseRotulo.getNome());
            assertEquals(ESTILO_ROTULO, responseRotulo.getEstilo());
            assertEquals(TEOR_ALCOOLICO_ROTULO, responseRotulo.getTeorAlcoolico());
        });

        verify(rotuloService).salvar(any(RotuloDTO.class));
    }

    static Stream<Arguments> dadosInvalidosParaCriacaoDeRorulos() {
        return Stream.of(
                Arguments.of("", "estilo", 4.5, "cervejaria", "Nome é obrigatório!"),
                Arguments.of("nome", "", 4.5, "cervejaria", "Estilo da cerveja é obrigatório!"),
                Arguments.of("nome", "estilo", -1, "cervejaria", "Teor alcoolico deve ser positivo!")
        );
    }

    @ParameterizedTest
    @MethodSource("dadosInvalidosParaCriacaoDeRorulos")
    void deveRetornar400AoCriarRotuloComNomeVazio(String nome, String estilo, double teor, String cervejaria, String messagemErro) {
        var dto = criarRotuloDTO(nome, estilo, teor, cervejaria);

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            String jsonBody = objectMapper.writeValueAsString(dto);
            var response = client.post("/rotulos", jsonBody);

            assertEquals(400, response.code());
            assert response.body() != null;
            assertTrue(response.body().string().contains(messagemErro));
        });

        verifyNoInteractions(rotuloService);
    }

    @Test
    void deveAtualizarRotulo() {
        var dto = buildRotuloDTO();
        var rotuloAtualiazado = buildRotulo();

        when(rotuloService.atualizar(ID_ROTULO, dto)).thenReturn(rotuloAtualiazado);

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            String jsonBody = objectMapper.writeValueAsString(dto);
                var response = client.put("/rotulos/"+ID_ROTULO, jsonBody);

            assertEquals(200, response.code());

            var responseBody = objectMapper.readValue(
                    response.body().string(),
                    Rotulo.class
            );

            assertEquals(NOME_ROTULO, responseBody.getNome());
        });

        verify(rotuloService).atualizar(anyInt(), any());
    }

    @Test
    void deveDeletarrotulo() {
        doNothing().when(rotuloService).deletar(ID_ROTULO);

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            var response = client.delete("/rotulos/"+ID_ROTULO);
            assertEquals(204, response.code());

            assert response.body() != null;
            assertEquals("", response.body().string());
        });

        verify(rotuloService).deletar(ID_ROTULO);
    }

    @Test
    void deveRetornar404AoDeletarRotuloInexistente() {
        doThrow(new NotFoundResponse("Rotulo nao existente")).when(rotuloService).deletar(ID_ROTULO);

        JavalinTest.test(criarAppComRotas(), (server, client) -> {
            var response = client.delete("/rotulos/"+ID_ROTULO);
            assertEquals(404, response.code());
        });

        verify(rotuloService).deletar(ID_ROTULO);
    }
}