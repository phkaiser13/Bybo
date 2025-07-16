// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package br.com.phkaiser.bybo.core.domain.repository;

import br.com.phkaiser.bybo.core.domain.entity.Livro;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface (Contrato) para o repositório de Livros.
 *
 * Esta interface define as operações de persistência de dados para a entidade Livro,
 * abstraindo completamente a forma como os dados são armazenados (em memória, banco de dados, arquivos, etc).
 * Qualquer classe que queira atuar como um repositório de livros DEVE implementar estes métodos.
 *
 * O uso de 'Optional' é uma prática moderna em Java para lidar com a possibilidade de um
 * resultado não ser encontrado, evitando o uso de 'null' e forçando quem chama o método
 * a tratar o caso de ausência do valor.
 */
public interface LivroRepository {

    /**
     * Salva um novo livro ou atualiza um existente.
     * Se o livro já tiver um ID que existe na base, ele deve ser atualizado.
     * Se for um livro novo, deve ser inserido.
     *
     * @param livro O objeto Livro a ser salvo. Não pode ser nulo.
     * @return O livro que foi salvo (pode conter informações atualizadas, como um ID gerado).
     */
    Livro salvar(Livro livro);

    /**
     * Busca um livro pelo seu ID único.
     *
     * @param id O UUID do livro a ser encontrado.
     * @return um Optional contendo o Livro se encontrado, ou um Optional vazio caso contrário.
     */
    Optional<Livro> buscarPorId(UUID id);

    /**
     * Retorna uma lista com todos os livros cadastrados no sistema.
     *
     * @return Uma List<Livro>. Se não houver livros, retorna uma lista vazia. Nunca retorna nulo.
     */
    List<Livro> buscarTodos();

    /**
     * Busca livros cujo título contenha a string fornecida (ignorando maiúsculas/minúsculas).
     *
     * @param titulo A parte do título a ser pesquisada.
     * @return Uma lista de livros que correspondem ao critério. Retorna lista vazia se nenhum for encontrado.
     */
    List<Livro> buscarPorTitulo(String titulo);

    /**
     * Busca livros cujo nome do autor contenha a string fornecida (ignorando maiúsculas/minúsculas).
     *
     * @param autor A parte do nome do autor a ser pesquisada.
     * @return Uma lista de livros que correspondem ao critério. Retorna lista vazia se nenhum for encontrado.
     */
    List<Livro> buscarPorAutor(String autor);

    /**
     * Deleta um livro da base de dados pelo seu ID.
     *
     * @param id O UUID do livro a ser deletado.
     */
    void deletarPorId(UUID id);
}