package br.com.phkaiser.bybo.persistence.repository;

import br.com.phkaiser.bybo.core.domain.entity.Item;
import br.com.phkaiser.bybo.core.domain.entity.Tabela;
import br.com.phkaiser.bybo.core.domain.repository.GenericRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GenericRepositoryFileMsgPack implements GenericRepository {

    private static final String DATA_DIR = ".bybo";
    private static final String SCHEMAS_FILE = "_schemas.mpack";
    private final Path schemasFilePath;
    private final Path dataDirPath;

    private final ObjectMapper objectMapper;
    private final Map<UUID, Tabela> tabelaCache = new ConcurrentHashMap<>();

    public GenericRepositoryFileMsgPack() {
        this.objectMapper = new ObjectMapper(new MessagePackFactory());
        this.objectMapper.registerModule(new JavaTimeModule()); // Para suportar tipos de data
        this.dataDirPath = Paths.get(System.getProperty("user.home"), DATA_DIR);
        this.schemasFilePath = dataDirPath.resolve(SCHEMAS_FILE);
        
        try {
            Files.createDirectories(dataDirPath);
            carregarTabelas();
        } catch (IOException e) {
            throw new RuntimeException("Falha ao inicializar o diretório de dados", e);
        }
    }

    // --- Métodos para Gerenciamento de Tabela ---

    public void salvarTabela(Tabela tabela) {
        tabelaCache.put(tabela.getId(), tabela);
        persistirCacheDeTabelas();
    }

    public Optional<Tabela> buscarTabelaPorId(UUID id) {
        return Optional.ofNullable(tabelaCache.get(id));
    }

    public List<Tabela> buscarTabelas() {
        return new ArrayList<>(tabelaCache.values());
    }

    private void carregarTabelas() throws IOException {
        if (Files.exists(schemasFilePath)) {
            byte[] bytes = Files.readAllBytes(schemasFilePath);
            if (bytes.length > 0) {
                List<Tabela> tabelas = objectMapper.readValue(bytes, new TypeReference<List<Tabela>>() {});
                tabelas.forEach(t -> tabelaCache.put(t.getId(), t));
            }
        }
    }

    private void persistirCacheDeTabelas() {
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(new ArrayList<>(tabelaCache.values()));
            Files.write(schemasFilePath, bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar esquemas de tabela", e);
        }
    }

    // --- Implementação da Interface GenericRepository (para Items) ---

    @Override
    public Item salvar(Item item) {
        Tabela tabela = tabelaCache.get(item.getTabelaId());
        if (tabela == null) {
            throw new IllegalArgumentException("Tabela com ID " + item.getTabelaId() + " não encontrada.");
        }

        Path dataFile = dataDirPath.resolve(tabela.getNomeArquivo());
        List<Item> items = carregarItemsDeArquivo(dataFile);

        // Remove o item antigo se for uma atualização
        items.removeIf(i -> i.getId().equals(item.getId()));
        items.add(item);

        salvarItemsEmArquivo(dataFile, items);
        return item;
    }

    @Override
    public Optional<Item> buscarPorId(UUID id) {
        // ATENÇÃO: Esta operação é ineficiente, pois varre todos os arquivos de dados.
        // É uma consequência do design da interface.
        for (Tabela tabela : tabelaCache.values()) {
            Path dataFile = dataDirPath.resolve(tabela.getNomeArquivo());
            Optional<Item> item = carregarItemsDeArquivo(dataFile).stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
            if (item.isPresent()) {
                return item;
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Item> buscarTodos(UUID tabelaId) {
        Tabela tabela = tabelaCache.get(tabelaId);
        if (tabela == null) {
            return Collections.emptyList();
        }
        Path dataFile = dataDirPath.resolve(tabela.getNomeArquivo());
        return carregarItemsDeArquivo(dataFile);
    }

    @Override
    public List<Item> buscarPorTermo(UUID tabelaId, String termo) {
        String termoLower = termo.toLowerCase();
        return buscarTodos(tabelaId).stream()
            .filter(item -> item.getValores().values().stream()
                .anyMatch(valor -> valor != null && valor.toString().toLowerCase().contains(termoLower)))
            .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(UUID id) {
        // Semelhante a buscarPorId, precisamos encontrar o item primeiro para saber de qual arquivo deletar.
        for (Tabela tabela : tabelaCache.values()) {
            Path dataFile = dataDirPath.resolve(tabela.getNomeArquivo());
            List<Item> items = carregarItemsDeArquivo(dataFile);
            
            Optional<Item> itemParaDeletar = items.stream().filter(i -> i.getId().equals(id)).findFirst();
            
            if (itemParaDeletar.isPresent()) {
                items.removeIf(i -> i.getId().equals(id));
                salvarItemsEmArquivo(dataFile, items);
                return; // Encontrou e deletou, pode sair.
            }
        }
    }
    
    // --- Métodos Auxiliares para Leitura/Escrita de Items ---

    private List<Item> carregarItemsDeArquivo(Path dataFile) {
        if (!Files.exists(dataFile)) {
            return new ArrayList<>();
        }
        try {
            byte[] bytes = Files.readAllBytes(dataFile);
            if (bytes.length == 0) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(bytes, new TypeReference<List<Item>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Falha ao carregar itens do arquivo: " + dataFile, e);
        }
    }

    private void salvarItemsEmArquivo(Path dataFile, List<Item> items) {
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(items);
            Files.write(dataFile, bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar itens no arquivo: " + dataFile, e);
        }
    }
}