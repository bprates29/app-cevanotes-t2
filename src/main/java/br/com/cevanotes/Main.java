package br.com.cevanotes;

import br.com.cevanotes.controller.RotuloController;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);

        app.get("/teste", ctx -> ctx.result("API Funcionando!"));
        new RotuloController().registrarRotas(app);
    }
}