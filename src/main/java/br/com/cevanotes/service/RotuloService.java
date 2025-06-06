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
        //rotulo.setCervejaria("nada haver");
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
        Rotulo rotulo = buscarPorId(id);
        aplicarAtualizacoes(rotulo, dto);
        repository.update(rotulo);
        return rotulo;
    }

    public void deletar(int id) {
        repository.delete(id);
    }

    private void aplicarAtualizacoes(Rotulo rotulo, RotuloDTO dto) {
        if (dto.getNome() != null) {
            rotulo.setNome(dto.getNome());
        }
        if (dto.getEstilo() != null) {
            rotulo.setEstilo(dto.getEstilo());
        }
        if (dto.getTeorAlcoolico() > 0) {
            rotulo.setTeorAlcoolico(dto.getTeorAlcoolico());
        }
        if (dto.getCervejaria() != null) {
            rotulo.setCervejaria(dto.getCervejaria());
        }

        rotulo.setDataCadastro(LocalDate.now());
    }


}
