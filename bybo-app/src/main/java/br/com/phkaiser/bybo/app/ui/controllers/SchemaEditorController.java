package br.com.phkaiser.bybo.app.ui.controllers;

import br.com.phkaiser.bybo.core.domain.entity.Campo;
import br.com.phkaiser.bybo.core.domain.entity.Tabela;
import br.com.phkaiser.bybo.core.domain.entity.TipoDado;
import br.com.phkaiser.bybo.persistence.repository.GenericRepositoryFileMsgPack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SchemaEditorController {

    @FXML
    private TextField tableNameField;
    @FXML
    private ListView<Campo> fieldsListView;
    @FXML
    private TextField fieldNameField;
    @FXML
    private ComboBox<TipoDado> fieldTypeComboBox;
    @FXML
    private TextField fieldOptionsField; // Novo campo para as opções
    @FXML
    private Button addFieldButton;
    @FXML
    private Button removeFieldButton;
    @FXML
    private Button saveTableButton;

    private GenericRepositoryFileMsgPack repository;
    private ObservableList<Campo> campos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        this.repository = new GenericRepositoryFileMsgPack();

        fieldTypeComboBox.setItems(FXCollections.observableArrayList(TipoDado.values()));

        // Mostra/esconde o campo de opções baseado na seleção do ComboBox
        fieldTypeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            boolean isList = newVal == TipoDado.LISTA_OPCOES;
            fieldOptionsField.setVisible(isList);
            fieldOptionsField.setManaged(isList);
        });

        fieldsListView.setItems(campos);
        fieldsListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<Campo>() {
            @Override
            public String toString(Campo campo) {
                if (campo.getTipo() == TipoDado.LISTA_OPCOES && campo.getOpcoes() != null) {
                    return String.format("%s (%s: %s)", campo.getNome(), campo.getTipo().name(), campo.getOpcoes());
                }
                return String.format("%s (%s)", campo.getNome(), campo.getTipo().name());
            }

            @Override
            public Campo fromString(String string) {
                return null;
            }
        }));

        addFieldButton.setOnAction(event -> adicionarCampo());
        removeFieldButton.setOnAction(event -> removerCampo());
        saveTableButton.setOnAction(event -> salvarTabela());
    }

    private void adicionarCampo() {
        String nomeCampo = fieldNameField.getText();
        TipoDado tipoDado = fieldTypeComboBox.getValue();

        if (nomeCampo == null || nomeCampo.trim().isEmpty() || tipoDado == null) {
            showAlert("Erro", "Nome do campo e tipo de dado são obrigatórios.");
            return;
        }

        Campo novoCampo;
        if (tipoDado == TipoDado.LISTA_OPCOES) {
            String opcoesTexto = fieldOptionsField.getText();
            if (opcoesTexto == null || opcoesTexto.trim().isEmpty()) {
                showAlert("Erro", "Para o tipo LISTA_OPCOES, as opções são obrigatórias.");
                return;
            }
            List<String> opcoes = Arrays.stream(opcoesTexto.split(","))
                                        .map(String::trim)
                                        .collect(Collectors.toList());
            novoCampo = new Campo(nomeCampo, tipoDado, opcoes);
        } else {
            novoCampo = new Campo(nomeCampo, tipoDado);
        }

        campos.add(novoCampo);
        fieldNameField.clear();
        fieldOptionsField.clear();
        fieldTypeComboBox.getSelectionModel().clearSelection();
    }

    private void removerCampo() {
        Campo selected = fieldsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            campos.remove(selected);
        } else {
            showAlert("Aviso", "Selecione um campo para remover.");
        }
    }

    private void salvarTabela() {
        String nomeTabela = tableNameField.getText();
        if (nomeTabela == null || nomeTabela.trim().isEmpty()) {
            showAlert("Erro", "O nome da tabela é obrigatório.");
            return;
        }
        if (campos.isEmpty()) {
            showAlert("Erro", "A tabela deve ter pelo menos um campo.");
            return;
        }

        String nomeArquivo = nomeTabela.trim().toLowerCase().replaceAll("\\s+", "_") + ".mpack";

        Tabela novaTabela = new Tabela(nomeTabela, nomeArquivo, campos.stream().collect(Collectors.toList()));
        repository.salvarTabela(novaTabela);

        showAlert("Sucesso", "Tabela '" + nomeTabela + "' salva com sucesso!");
        tableNameField.clear();
        campos.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}