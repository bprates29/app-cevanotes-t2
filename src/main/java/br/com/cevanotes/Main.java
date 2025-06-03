package br.com.cevanotes;

import br.com.cevanotes.config.DbConfig;
import br.com.cevanotes.controller.RotuloController;
import br.com.cevanotes.repository.RotuloRepository;
import br.com.cevanotes.service.RotuloService;
import io.javalin.Javalin;
import org.jetbrains.annotations.NotNull;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);
        var rotuloService = instanciarTodasAsClasses();


        app.get("/teste", ctx -> ctx.result("API Funcionando!"));
        new RotuloController(rotuloService).registrarRotas(app);
    }

    @NotNull
    private static RotuloService instanciarTodasAsClasses() {
        var dbConfig = DbConfig.createJdbi();
        var rotuloRepository = new RotuloRepository(dbConfig);
        return new RotuloService(rotuloRepository);
    }
}