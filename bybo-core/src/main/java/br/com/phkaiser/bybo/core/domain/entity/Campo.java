package br.com.phkaiser.bybo.core.domain.entity;

import java.util.List;

public class Campo {
    private String nome;
    private TipoDado tipo;
    private List<String> opcoes; // Usado apenas se o tipo for LISTA_OPCOES

    public Campo() { // Construtor padrão para deserialização
    }

    public Campo(String nome, TipoDado tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }
    
    public Campo(String nome, TipoDado tipo, List<String> opcoes) {
        this.nome = nome;
        this.tipo = tipo;
        this.opcoes = opcoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoDado getTipo() {
        return tipo;
    }

    public void setTipo(TipoDado tipo) {
        this.tipo = tipo;
    }

    public List<String> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(List<String> opcoes) {
        this.opcoes = opcoes;
    }
}