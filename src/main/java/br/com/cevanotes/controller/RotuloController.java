package br.com.cevanotes.controller;

import br.com.cevanotes.config.DbConfig;
import br.com.cevanotes.dto.RotuloDTO;
import br.com.cevanotes.repository.RotuloRepository;
import br.com.cevanotes.service.RotuloService;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.InternalServerErrorResponse;

public class RotuloController {
    private final RotuloService service;

    public RotuloController (RotuloService service) {
        this.service = service;
    }

    public void registrarRotas(Javalin app) {
        app.get("/rotulos", ctx -> ctx.json(service.listar()));
        app.get("/rotulos/{id}", ctx -> {
            var id = parseIdParam(ctx);
            ctx.json(service.buscarPorId(id));
        });
        app.post("/rotulos", ctx -> {
            RotuloDTO dto = ctx.bodyValidator(RotuloDTO.class)
                    .check(r -> r.getNome() != null && !r.getNome().isBlank(), "Nome é obrigatório!")
                    .check(r -> r.getEstilo() != null && !r.getEstilo().isBlank(), "Estilo da cerveja é obrigatório!")
                    .check(r -> r.getTeorAlcoolico() >= 0, "Teor alcoolico deve ser positivo!")
                    .get();
            var novoRotulo = service.salvar(dto);
            ctx.status(201).json(novoRotulo);
        });
        app.put("/rotulos/{id}", ctx -> {
           var id = parseIdParam(ctx);
           var r = ctx.bodyAsClass(RotuloDTO.class);
           var update = service.atualizar(id, r);
           ctx.status(200).json(update);
        });
    }

    private int parseIdParam(Context ctx) {
        try {
            return Integer.parseInt(ctx.pathParam("id"));
        } catch (NumberFormatException e) {
            throw new BadRequestResponse("ID inválido. Use um numero inteiro!");
        }
    }
}
