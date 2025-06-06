package br.com.cevanotes;

import br.com.cevanotes.config.DbConfig;
import br.com.cevanotes.controller.RotuloController;
import br.com.cevanotes.repository.RotuloRepository;
import br.com.cevanotes.service.RotuloService;
import io.javalin.Javalin;
import io.javalin.http.UnauthorizedResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final String TOKEN_SECRETO = "meu-token-infnet-123";
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);
        var rotuloService = instanciarTodasAsClasses();

        validacaoDeAcesso(app);
        app.get("/teste", ctx -> ctx.result("API Funcionando!"));

        app.after(ctx -> {
            log.info("Requisição {} {} -> status {}", ctx.method(), ctx.path(), ctx.status());
        });

        new RotuloController(rotuloService).registrarRotas(app);
    }

    private static void validacaoDeAcesso(Javalin app) {
        app.before(ctx -> {
            String rota = ctx.path();
            if (rota.startsWith("/teste")) {
                return;
            }

            String tokenRecebido = ctx.header("Authorization");

            if (tokenRecebido == null || !tokenRecebido.equals(TOKEN_SECRETO)) {
                throw new UnauthorizedResponse("Token inválido ou ausente!");
            }
        });
    }

    @NotNull
    private static RotuloService instanciarTodasAsClasses() {
        var dbConfig = DbConfig.createJdbi();
        var rotuloRepository = new RotuloRepository(dbConfig);
        return new RotuloService(rotuloRepository);
    }
}