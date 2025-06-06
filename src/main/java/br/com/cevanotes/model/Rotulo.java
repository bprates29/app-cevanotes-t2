package br.com.cevanotes.model;

import java.time.LocalDate;
import java.util.Objects;

public class Rotulo {
    private Integer id;
    private String nome;
    private String estilo;
    private double teorAlcoolico;
    private String cervejaria;
    private LocalDate dataCadastro;

    public Rotulo() {
    }

    public Rotulo(Integer id, String nome, String estilo, double teorAlcoolico, String cervejaria, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.estilo = estilo;
        this.teorAlcoolico = teorAlcoolico;
        this.cervejaria = cervejaria;
        this.dataCadastro = dataCadastro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rotulo rotulo = (Rotulo) o;
        return Double.compare(teorAlcoolico, rotulo.teorAlcoolico) == 0 && Objects.equals(nome, rotulo.nome) && Objects.equals(estilo, rotulo.estilo) && Objects.equals(cervejaria, rotulo.cervejaria) && Objects.equals(dataCadastro, rotulo.dataCadastro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, estilo, teorAlcoolico, cervejaria, dataCadastro);
    }


    @Override
    public String toString() {
        return "Rotulo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", estilo='" + estilo + '\'' +
                ", teorAlcoolico=" + teorAlcoolico +
                ", cervejaria='" + cervejaria + '\'' +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}
