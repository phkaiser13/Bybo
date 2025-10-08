package br.com.phkaiser.bybo.core.domain.entity;

import java.util.Map;
import java.util.UUID;

// Representa um registro genérico em qualquer tabela
public class Item {
    private UUID id;
    private UUID tabelaId; // Link para a Tabela a que pertence
    private Map<String, Object> valores; // Chave: nome do Campo, Valor: dado do usuário

    public Item() {
        this.id = UUID.randomUUID();
    }

    public Item(UUID tabelaId, Map<String, Object> valores) {
        this.id = UUID.randomUUID();
        this.tabelaId = tabelaId;
        this.valores = valores;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTabelaId() {
        return tabelaId;
    }

    public void setTabelaId(UUID tabelaId) {
        this.tabelaId = tabelaId;
    }

    public Map<String, Object> getValores() {
        return valores;
    }

    public void setValores(Map<String, Object> valores) {
        this.valores = valores;
    }
}