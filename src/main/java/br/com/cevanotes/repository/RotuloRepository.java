package br.com.cevanotes.repository;

import br.com.cevanotes.model.Rotulo;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

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
}
