// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package br.com.phkaiser.bybo.core.domain.entity;

import java.util.Objects;
import java.util.UUID;

/**
 * Representa um livro no nosso sistema.
 * Esta é uma classe POJO (Plain Old Java Object) clássica.
 * Note que ela importa e utiliza o enum 'StatusLivro' que foi escrito em Kotlin,
 * demonstrando a perfeita interoperabilidade entre as duas linguagens.
 */
public final class Livro {

    // --- CAMPOS (FIELDS) ---
    private UUID id;
    private String titulo;
    private String autor;
    private String isbn;
    private int anoPublicacao;
    private StatusLivro status;

    /**
     * Construtor padrão (sem argumentos).
     * O Jackson usará este construtor para criar uma instância vazia do objeto
     * antes de preencher os campos com os dados do arquivo.
     */
    public Livro() {
    }

    /**
     * Construtor para criar uma nova instância de Livro.
     * O ID é gerado automaticamente e o status inicial é sempre DISPONIVEL.
     */
    public Livro(String titulo, String autor, String isbn, int anoPublicacao) {
        this.id = UUID.randomUUID(); // Garante um ID único para cada livro.
        this.titulo = Objects.requireNonNull(titulo, "Título não pode ser nulo");
        this.autor = Objects.requireNonNull(autor, "Autor não pode ser nulo");
        this.isbn = Objects.requireNonNull(isbn, "ISBN não pode ser nulo");
        this.anoPublicacao = anoPublicacao;
        this.status = StatusLivro.DISPONIVEL; // Todo livro novo começa como disponível.
    }

    // --- MÉTODOS DE ACESSO (GETTERS) ---
    // Fornecem acesso de leitura aos campos privados.
    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public StatusLivro getStatus() {
        return status;
    }

    // --- MÉTODOS DE MODIFICAÇÃO (SETTERS) ---
    // Apenas para os campos que podem ser alterados.
    public void setId(UUID id) {
        this.id = id;
    }

    public void setStatus(StatusLivro status) {
        this.status = Objects.requireNonNull(status, "Status não pode ser nulo");
    }

    // Setters adicionados
    public void setTitulo(String titulo) {
        this.titulo = Objects.requireNonNull(titulo, "Título não pode ser nulo");
    }

    public void setAutor(String autor) {
        this.autor = Objects.requireNonNull(autor, "Autor não pode ser nulo");
    }

    public void setIsbn(String isbn) {
        this.isbn = Objects.requireNonNull(isbn, "ISBN não pode ser nulo");
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }


    // --- MÉTODOS PADRÃO (equals, hashCode, toString) ---

    /**
     * O método toString() é usado para criar uma representação em String do objeto.
     * É muito útil para logs e debugging.
     */
    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", anoPublicacao=" + anoPublicacao +
                ", status=" + status +
                '}';
    }

    /**
     * O método equals() compara dois objetos para ver se são "iguais".
     * A boa prática é considerar dois livros iguais se eles tiverem o mesmo ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id);
    }

    /**
     * O método hashCode() retorna um código hash para o objeto.
     * Se você sobrescreve equals(), você DEVE sobrescrever hashCode().
     * É usado por estruturas de dados como HashMap e HashSet para otimizar a busca.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}