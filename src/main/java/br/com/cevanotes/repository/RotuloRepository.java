package br.com.cevanotes.repository;

import br.com.cevanotes.model.Rotulo;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

public class RotuloRepository {
    private final Jdbi dataSource;

    public RotuloRepository (Jdbi jdbi) {
        this.dataSource = jdbi;
    }

    public List<Rotulo> findAll() {
        return dataSource.withHandle(handle ->
                handle.createQuery("SELECT * FROM rotulos")
                        .mapToBean(Rotulo.class)
                        .list());
    }

    public Optional<Rotulo> findById(int id) {
        return dataSource.withHandle(handle ->
                handle.createQuery("SELECT * FROM rotulos WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Rotulo.class)
                        .findOne());
    }

    public int insert(Rotulo r) {
        return dataSource.withHandle(handle ->
                handle.createUpdate("INSERT INTO rotulos (nome, estilo, teor_alcoolico, cervejaria, data_cadastro) " +
                        "VALUES (:nome, :estilo, :teorAlcoolico, :cervejaria, :dataCadastro)")
                        .bindBean(r)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one());
    }

    public void update(Rotulo r) {
        dataSource.withHandle(handle ->
                handle.createUpdate("UPDATE rotulos SET " +
                                "nome = :nome, " +
                                "estilo = :estilo, " +
                                "teor_alcoolico = :teorAlcoolico, " +
                                "cervejaria = :cervejaria, " +
                                "data_cadastro = :dataCadastro " +
                                "WHERE id = :id")
                        .bindBean(r)
                        .execute());
    }

    public void delete(int id) {
        dataSource.useHandle(handle ->
                handle.createUpdate("DELETE FROM rotulos WHERE id = :id")
                        .bind("id", id)
                        .execute());
    }
}
