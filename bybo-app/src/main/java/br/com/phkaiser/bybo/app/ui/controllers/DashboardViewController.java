package br.com.phkaiser.bybo.app.ui.controllers;

import br.com.phkaiser.bybo.core.domain.entity.Campo;
import br.com.phkaiser.bybo.core.domain.entity.Item;
import br.com.phkaiser.bybo.core.domain.entity.Tabela;
import br.com.phkaiser.bybo.core.domain.entity.TipoDado;
import br.com.phkaiser.bybo.persistence.repository.GenericRepositoryFileMsgPack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DashboardViewController {

    @FXML private ComboBox<Tabela> tableSelectorComboBox;
    @FXML private Label totalItemsLabel;
    @FXML private PieChart distributionChart;
    @FXML private Label chartPlaceholderLabel;

    private GenericRepositoryFileMsgPack repository;

    @FXML
    public void initialize() {
        this.repository = new GenericRepositoryFileMsgPack();
        
        configurarSeletorDeTabela();
        
        tableSelectorComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                atualizarDashboard(newVal);
            }
        });
    }

    private void configurarSeletorDeTabela() {
        List<Tabela> tabelas = repository.buscarTabelas();
        tableSelectorComboBox.setItems(FXCollections.observableArrayList(tabelas));

        // Isso faz com que o ComboBox mostre o nome da tabela, mas mantenha o objeto Tabela inteiro
        tableSelectorComboBox.setConverter(new StringConverter<Tabela>() {
            @Override
            public String toString(Tabela tabela) {
                return tabela != null ? tabela.getNome() : "";
            }

            @Override
            public Tabela fromString(String string) {
                return null; // Não é necessário para nossa implementação
            }
        });
    }

    private void atualizarDashboard(Tabela tabela) {
        List<Item> items = repository.buscarTodos(tabela.getId());

        // 1. Atualizar o total de itens
        totalItemsLabel.setText(String.valueOf(items.size()));

        // 2. Procurar por um campo do tipo LISTA_OPCOES para o gráfico
        Optional<Campo> campoDeOpcoes = tabela.getCampos().stream()
                .filter(c -> c.getTipo() == TipoDado.LISTA_OPCOES)
                .findFirst();

        if (campoDeOpcoes.isPresent()) {
            gerarGraficoDePizza(items, campoDeOpcoes.get());
            distributionChart.setVisible(true);
            chartPlaceholderLabel.setVisible(false);
        } else {
            distributionChart.setVisible(false);
            chartPlaceholderLabel.setVisible(true);
            chartPlaceholderLabel.setText("A tabela '" + tabela.getNome() + "' não possui campos do tipo 'LISTA_OPCOES'.");
        }
    }

    private void gerarGraficoDePizza(List<Item> items, Campo campo) {
        // Contar a ocorrência de cada opção
        Map<String, Long> contagem = items.stream()
                .map(item -> item.getValores().get(campo.getNome()))
                .filter(valor -> valor != null)
                .map(Object::toString)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Criar os dados para o gráfico
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        contagem.forEach((opcao, total) -> {
            pieChartData.add(new PieChart.Data(opcao + " (" + total + ")", total));
        });
        
        distributionChart.setData(pieChartData);
        distributionChart.setTitle("Distribuição por '" + campo.getNome() + "'");
    }
}