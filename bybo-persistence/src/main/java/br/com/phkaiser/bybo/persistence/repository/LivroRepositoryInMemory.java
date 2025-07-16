// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package br.com.phkaiser.bybo.persistence.repository;

import br.com.phkaiser.bybo.core.domain.entity.Livro;
import br.com.phkaiser.bybo.core.domain.repository.LivroRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementação em memória do repositório de Livros.
 *
 * Esta classe cumpre o contrato definido pela interface 'LivroRepository'.
 * Ela utiliza um 'ConcurrentHashMap' para armazenar os livros. A chave do mapa é o ID do livro (UUID)
 * e o valor é o próprio objeto Livro.
 *
 * O uso de 'ConcurrentHashMap' torna a nossa implementação "thread-safe", o que significa que
 * ela pode ser acessada por múltiplas partes do programa simultaneamente sem risco de corromper os dados.
 * É uma boa prática, mesmo em aplicações de console simples.
 */
public class LivroRepositoryInMemory implements LivroRepository {

    // O 'static final' garante que teremos apenas UMA instância deste mapa em toda a aplicação (padrão Singleton).
    // Todos os dados dos livros viverão aqui enquanto o programa estiver em execução.
    private static final Map<UUID, Livro> DADOS = new ConcurrentHashMap<>();

    @Override
    public Livro salvar(Livro livro) {
        Objects.requireNonNull(livro, "O livro para salvar não pode ser nulo.");
        // O método put() do Map faz exatamente o que precisamos:
        // Se a chave (livro.getId()) não existe, ele insere.
        // Se a chave já existe, ele atualiza o valor associado a ela.
        DADOS.put(livro.getId(), livro);
        return livro;
    }

    @Override
    public Optional<Livro> buscarPorId(UUID id) {
        // Optional.ofNullable() é a forma segura de encapsular um valor que pode ser nulo.
        // Se DADOS.get(id) retornar um livro, o Optional o conterá.
        // Se retornar nulo, teremos um Optional.empty().
        return Optional.ofNullable(DADOS.get(id));
    }

    @Override
    public List<Livro> buscarTodos() {
        // Retornamos uma nova ArrayList contendo todos os valores do mapa.
        // Criar uma nova lista garante que quem chama este método não possa modificar
        // nossa lista interna de dados diretamente.
        return new ArrayList<>(DADOS.values());
    }

    @Override
    public List<Livro> buscarPorTitulo(String titulo) {
        // Usamos a API de Streams do Java 8+, uma forma moderna e funcional de processar coleções.
        return DADOS.values().stream() // 1. Cria um fluxo (stream) de todos os livros.
                .filter(livro -> livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) // 2. Filtra, mantendo apenas os livros cujo título (em minúsculas) contém a string de busca (também em minúsculas).
                .collect(Collectors.toList()); // 3. Coleta os resultados em uma nova lista.
    }

    @Override
    public List<Livro> buscarPorAutor(String autor) {
        // A lógica é idêntica à busca por título, mas aplicada ao campo do autor.
        return DADOS.values().stream()
                .filter(livro -> livro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(UUID id) {
        // Simplesmente remove a entrada do mapa usando a chave (ID).
        DADOS.remove(id);
    }
}