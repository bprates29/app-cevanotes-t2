package br.com.cevanotes.controller;

import br.com.cevanotes.model.Rotulo;
import br.com.cevanotes.service.RotuloService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        Rotulo rotulo1 = new Rotulo();
        rotulo1.setId(1);
        rotulo1.setNome("Heineken");
        rotulo1.setEstilo("Larger");
        rotulo1.setTeorAlcoolico(5.0);
        rotulo1.setCervejaria("Heinenken");

        Rotulo rotulo2 = new Rotulo();
        rotulo2.setId(2);
        rotulo2.setNome("Stella");
        rotulo2.setEstilo("Pilsner");
        rotulo2.setTeorAlcoolico(4.0);
        rotulo2.setCervejaria("Stella");

        List<Rotulo> rotulos = List.of(rotulo1, rotulo2);
        Mockito.when(rotuloService.listar()).thenReturn(rotulos);

        Javalin app = Javalin.create();
        rotuloController.registrarRotas(app);

        JavalinTest.test(app, (server, client) -> {
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

        Mockito.verify(rotuloService).listar();
    }

}