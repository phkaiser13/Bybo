package br.com.phkaiser.bybo.app.ui.controllers;

import br.com.phkaiser.bybo.app.service.ExportService;
import br.com.phkaiser.bybo.core.domain.entity.Campo;
import br.com.phkaiser.bybo.core.domain.entity.Item;
import br.com.phkaiser.bybo.core.domain.entity.Tabela;
import br.com.phkaiser.bybo.core.domain.entity.TipoDado;
import br.com.phkaiser.bybo.persistence.repository.GenericRepositoryFileMsgPack;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class GenericDataViewController {

    @FXML private Label tableNameLabel;
    @FXML private TextField searchField;
    @FXML private TableView<Item> itemsTableView;
    @FXML private GridPane formGridPane;
    @FXML private Button saveButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Button exportButton; // Botão de exportação

    private Tabela tabela;
    private GenericRepositoryFileMsgPack repository;
    private ExportService exportService;
    private ObservableList<Item> itemsObservableList = FXCollections.observableArrayList();
    private Map<String, Node> formControls = new HashMap<>();
    private Item selectedItem;

    @FXML
    public void initialize() {
        this.repository = new GenericRepositoryFileMsgPack();
        this.exportService = new ExportService();

        itemsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedItem = newSelection;
            popularFormulario(newSelection);
        });

        searchField.textProperty().addListener((obs, oldVal, newVal) -> buscarItems(newVal));

        saveButton.setOnAction(event -> salvarItem());
        deleteButton.setOnAction(event -> deletarItem());
        clearButton.setOnAction(event -> limparFormulario());
        exportButton.setOnAction(event -> exportarParaExcel()); // Ação do novo botão
    }

    public void carregarDadosDaTabela(Tabela tabela) {
        this.tabela = tabela;
        tableNameLabel.setText(tabela.getNome());

        montarTabela();
        montarFormulario();
        carregarItemsNaTabela();
    }

    private void montarTabela() {
        itemsTableView.getColumns().clear();
        for (Campo campo : tabela.getCampos()) {
            TableColumn<Item, Object> column = new TableColumn<>(campo.getNome());
            column.setCellValueFactory(cellData -> {
                Object value = cellData.getValue().getValores().get(campo.getNome());
                return new SimpleObjectProperty<>(value);
            });
            itemsTableView.getColumns().add(column);
        }
        itemsTableView.setItems(itemsObservableList);
    }

    private void montarFormulario() {
        formGridPane.getChildren().clear();
        formControls.clear();
        int rowIndex = 0;
        for (Campo campo : tabela.getCampos()) {
            Label label = new Label(campo.getNome());
            Node control = criarControlParaCampo(campo);
            formGridPane.add(label, 0, rowIndex);
            formGridPane.add(control, 1, rowIndex);
            formControls.put(campo.getNome(), control);
            rowIndex++;
        }
    }

    private Node criarControlParaCampo(Campo campo) {
        switch (campo.getTipo()) {
            case NUMERO:
                TextField numericField = new TextField();
                numericField.setPromptText("Apenas números");
                return numericField;
            case DATA:
                return new DatePicker();
            case LISTA_OPCOES:
                ComboBox<String> comboBox = new ComboBox<>();
                if (campo.getOpcoes() != null) {
                    comboBox.setItems(FXCollections.observableArrayList(campo.getOpcoes()));
                }
                return comboBox;
            case TEXTO:
            default:
                return new TextField();
        }
    }

    private void carregarItemsNaTabela() {
        itemsObservableList.setAll(repository.buscarTodos(tabela.getId()));
    }

    private void buscarItems(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            carregarItemsNaTabela();
        } else {
            itemsObservableList.setAll(repository.buscarPorTermo(tabela.getId(), termo));
        }
    }

    private void popularFormulario(Item item) {
        if (item == null) {
            limparFormulario();
            return;
        }
        for (Map.Entry<String, Node> entry : formControls.entrySet()) {
            String nomeCampo = entry.getKey();
            Node control = entry.getValue();
            Object valor = item.getValores().get(nomeCampo);

            if (control instanceof TextField) {
                ((TextField) control).setText(valor != null ? valor.toString() : "");
            } else if (control instanceof DatePicker) {
                if (valor != null && !valor.toString().isEmpty()) {
                    ((DatePicker) control).setValue(LocalDate.parse(valor.toString()));
                } else {
                    ((DatePicker) control).setValue(null);
                }
            } else if (control instanceof ComboBox) {
                ((ComboBox<String>) control).setValue(valor != null ? valor.toString() : null);
            }
        }
    }

    private void salvarItem() {
        Map<String, Object> valores = new HashMap<>();
        for (Campo campo : tabela.getCampos()) {
            Node control = formControls.get(campo.getNome());
            Object valor = null;
            if (control instanceof TextField) {
                valor = ((TextField) control).getText();
                if (campo.getTipo() == TipoDado.NUMERO && valor != null && !((String)valor).isEmpty()) {
                    try {
                        valor = Double.parseDouble((String) valor);
                    } catch (NumberFormatException e) {
                        showAlert("Erro de Formato", "O campo '" + campo.getNome() + "' deve ser um número.");
                        return;
                    }
                }
            } else if (control instanceof DatePicker) {
                LocalDate date = ((DatePicker) control).getValue();
                if (date != null) {
                    valor = date.toString();
                }
            } else if (control instanceof ComboBox) {
                valor = ((ComboBox<?>) control).getValue();
            }
            valores.put(campo.getNome(), valor);
        }

        Item itemParaSalvar;
        if (selectedItem != null) {
            itemParaSalvar = selectedItem;
            itemParaSalvar.setValores(valores);
        } else {
            itemParaSalvar = new Item(tabela.getId(), valores);
        }

        repository.salvar(itemParaSalvar);
        carregarItemsNaTabela();
        limparFormulario();
    }

    private void deletarItem() {
        if (selectedItem == null) {
            showAlert("Aviso", "Selecione um item para deletar.");
            return;
        }
        repository.deletarPorId(selectedItem.getId());
        carregarItemsNaTabela();
        limparFormulario();
    }

    private void limparFormulario() {
        itemsTableView.getSelectionModel().clearSelection();
        selectedItem = null;
        for (Node control : formControls.values()) {
            if (control instanceof TextField) {
                ((TextField) control).clear();
            } else if (control instanceof DatePicker) {
                ((DatePicker) control).setValue(null);
            } else if (control instanceof ComboBox) {
                ((ComboBox<?>) control).getSelectionModel().clearSelection();
            }
        }
    }

    private void exportarParaExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Planilha do Excel");
        fileChooser.setInitialFileName(tabela.getNomeArquivo().replace(".mpack", ".xlsx"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo Excel (*.xlsx)", "*.xlsx"));
        
        File file = fileChooser.showSaveDialog(exportButton.getScene().getWindow());

        if (file != null) {
            try {
                exportService.exportarParaExcel(tabela, itemsObservableList, file);
                showAlert("Sucesso", "Dados exportados com sucesso para:\n" + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erro de Exportação", "Ocorreu um erro ao salvar o arquivo:\n" + e.getMessage());
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}