package br.com.phkaiser.bybo.core.domain.repository;

import br.com.phkaiser.bybo.core.domain.entity.Item;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenericRepository {
    Item salvar(Item item);
    Optional<Item> buscarPorId(UUID id);
    List<Item> buscarTodos(UUID tabelaId);
    List<Item> buscarPorTermo(UUID tabelaId, String termo);
    void deletarPorId(UUID id);
}