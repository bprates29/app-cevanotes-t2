package br.com.cevanotes.dto;

import java.util.Objects;

public class RotuloDTO {
    private String nome;
    private String estilo;
    private double teorAlcoolico;
    private String cervejaria;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public double getTeorAlcoolico() {
        return teorAlcoolico;
    }

    public void setTeorAlcoolico(double teorAlcoolico) {
        this.teorAlcoolico = teorAlcoolico;
    }

    public String getCervejaria() {
        return cervejaria;
    }

    public void setCervejaria(String cervejaria) {
        this.cervejaria = cervejaria;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RotuloDTO rotuloDTO = (RotuloDTO) o;
        return Double.compare(teorAlcoolico, rotuloDTO.teorAlcoolico) == 0 && Objects.equals(nome, rotuloDTO.nome) && Objects.equals(estilo, rotuloDTO.estilo) && Objects.equals(cervejaria, rotuloDTO.cervejaria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, estilo, teorAlcoolico, cervejaria);
    }

    @Override
    public String toString() {
        return "RotuloDTO{" +
                "nome='" + nome + '\'' +
                ", estilo='" + estilo + '\'' +
                ", teorAlcoolico=" + teorAlcoolico +
                ", cervejaria='" + cervejaria + '\'' +
                '}';
    }
}
