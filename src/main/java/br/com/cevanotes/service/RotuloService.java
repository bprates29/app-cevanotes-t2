package br.com.cevanotes.service;

import br.com.cevanotes.dto.RotuloDTO;
import br.com.cevanotes.model.Rotulo;
import br.com.cevanotes.repository.RotuloRepository;
import io.javalin.http.NotFoundResponse;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

public class RotuloService {
    private final RotuloRepository repository;

    public RotuloService (RotuloRepository repository) {
        this.repository = repository;
    }

    public List<Rotulo> listar() {
        return repository.findAll();
    }

    public Rotulo buscarPorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Rótulo não encontrado!"));
    }

    public Rotulo salvar(RotuloDTO dto) {
        Rotulo rotulo = construirRotuloAPartirDoDTO(dto);

        var id = repository.insert(rotulo);
        return buscarPorId(id);
    }

    @NotNull
    private static Rotulo construirRotuloAPartirDoDTO(RotuloDTO dto) {
        Rotulo rotulo = new Rotulo();
        rotulo.setNome(dto.getNome());
        rotulo.setEstilo(dto.getEstilo());
        rotulo.setTeorAlcoolico(dto.getTeorAlcoolico());
        rotulo.setCervejaria(dto.getCervejaria());
        rotulo.setDataCadastro(LocalDate.now());
        return rotulo;
    }

    public Rotulo atualizar(int id, RotuloDTO dto) {
        if (repository.findById(id).isEmpty()) {
            throw new NotFoundResponse("Rotulo não encontrado!");
        }
        Rotulo rotulo = construirRotuloAPartirDoDTO(dto);

        rotulo.setId(id);

        repository.update(rotulo);
        return buscarPorId(id);
    }
}
