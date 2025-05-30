package br.com.cevanotes.model;

import java.time.LocalDate;

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
}
