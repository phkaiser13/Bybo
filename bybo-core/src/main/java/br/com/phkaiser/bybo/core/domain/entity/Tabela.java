package br.com.phkaiser.bybo.core.domain.entity;

import java.util.List;
import java.util.UUID;

// Define a estrutura de uma tabela customizada (Ex: "Equipamentos", "Livros", "Componentes")
public class Tabela {
    private UUID id;
    private String nome; // Ex: "Empréstimos de Arduinos"
    private String nomeArquivo; // Ex: "emprestimos_arduinos.mpack"
    private List<Campo> campos; // A lista de colunas/campos da tabela

    public Tabela() {
        this.id = UUID.randomUUID();
    }

    public Tabela(String nome, String nomeArquivo, List<Campo> campos) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.nomeArquivo = nomeArquivo;
        this.campos = campos;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public List<Campo> getCampos() {
        return campos;
    }

    public void setCampos(List<Campo> campos) {
        this.campos = campos;
    }
}