// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package br.com.phkaiser.bybo.persistence.repository;

import br.com.phkaiser.bybo.core.domain.entity.Livro;
import br.com.phkaiser.bybo.core.domain.repository.LivroRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementação do repositório de Livros que persiste os dados em um arquivo usando o formato MessagePack.
 * Esta abordagem garante que os dados não sejam perdidos quando a aplicação é fechada.
 */
public class LivroRepositoryFileMsgPack implements LivroRepository {

    // O ObjectMapper é o objeto do Jackson que faz a mágica da conversão.
    // Configuramos ele com a MessagePackFactory() para que ele trabalhe com o formato binário.
    private final ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());

    // O caminho para o nosso arquivo de dados. Usar o diretório home do usuário é uma prática robusta.
    private static final String DATA_DIR = System.getProperty("user.home") + File.separator + ".bybo";
    private static final String FILE_PATH = DATA_DIR + File.separator + "bybo_data.mpack";

    // Mantemos um cache em memória para acesso rápido. Os dados são carregados do arquivo para este mapa.
    private final Map<UUID, Livro> dados;

    /**
     * O construtor é o ponto de partida. Ele garante que o diretório de dados exista
     * e carrega os livros do arquivo, se ele existir.
     */
    public LivroRepositoryFileMsgPack() {
        try {
            // Garante que o diretório ~/.bybo exista.
            Files.createDirectories(Paths.get(DATA_DIR));
        } catch (IOException e) {
            System.err.println("Falha ao criar diretório de dados: " + e.getMessage());
        }
        this.dados = carregarDoArquivo();
    }

    @Override
    public Livro salvar(Livro livro) {
        Objects.requireNonNull(livro, "O livro para salvar não pode ser nulo.");
        dados.put(livro.getId(), livro);
        salvarNoArquivo(); // A cada alteração, persistimos no disco.
        return livro;
    }

    @Override
    public Optional<Livro> buscarPorId(UUID id) {
        return Optional.ofNullable(dados.get(id));
    }

    @Override
    public List<Livro> buscarTodos() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public List<Livro> buscarPorTitulo(String titulo) {
        return dados.values().stream()
                .filter(livro -> livro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Livro> buscarPorAutor(String autor) {
        return dados.values().stream()
                .filter(livro -> livro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(UUID id) {
        dados.remove(id);
        salvarNoArquivo(); // A cada alteração, persistimos no disco.
    }

    /**
     * Rotina para carregar os dados do arquivo .mpack para o mapa em memória.
     * @return Um mapa com os dados carregados, ou um mapa vazio se o arquivo não existir ou ocorrer um erro.
     */
    private Map<UUID, Livro> carregarDoArquivo() {
        File arquivo = new File(FILE_PATH);
        if (arquivo.exists() && arquivo.length() > 0) {
            try {
                // Usamos TypeReference para dizer ao Jackson como reconstruir nosso mapa genérico (Map<UUID, Livro>).
                return objectMapper.readValue(arquivo, new TypeReference<ConcurrentHashMap<UUID, Livro>>() {});
            } catch (IOException e) {
                System.err.println("Erro ao carregar dados do arquivo: " + e.getMessage());
            }
        }
        // Se o arquivo não existe ou está vazio, começa com um mapa novo.
        return new ConcurrentHashMap<>();
    }

    /**
     * Rotina para salvar o estado atual do mapa em memória para o arquivo .mpack.
     * É sincronizada para evitar problemas de concorrência se a aplicação fosse multi-thread.
     */
    private synchronized void salvarNoArquivo() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), dados);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados no arquivo: " + e.getMessage());
        }
    }
}