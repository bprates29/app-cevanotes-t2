package br.com.cevanotes.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConfig {

    public static Jdbi createJdbi() {
        var ds = createDaSouce();
        try (Connection conn = ds.getConnection()) {
            runScript(conn, "script.sql");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return Jdbi.create(ds);
    }

    private static DataSource createDaSouce() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        config.setUsername("sa");
        config.setPassword("");
        return new HikariDataSource(config);
    }

    private static void runScript(Connection conn, String resource) throws IOException, SQLException {
        var input = DbConfig.class.getClassLoader().getResourceAsStream(resource);
        if (input == null) throw new IOException("Arquivo não encontrado: " + resource);
        var sql = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        try (var stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}
