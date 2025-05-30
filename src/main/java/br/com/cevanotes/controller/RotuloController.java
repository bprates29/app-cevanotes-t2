package br.com.cevanotes.controller;

import br.com.cevanotes.config.DbConfig;
import br.com.cevanotes.repository.RotuloRepository;
import io.javalin.Javalin;
import io.javalin.http.InternalServerErrorResponse;

public class RotuloController {

    public void registrarRotas(Javalin app) {
        try {
            var repository =  new RotuloRepository(DbConfig.createJdbi());
            app.get("/rotulos", ctx -> ctx.json(repository.findAll()));
        } catch (Exception e) {
            throw new InternalServerErrorResponse("Servidor do banco de dados caiu gente!");
        }
    }
}
