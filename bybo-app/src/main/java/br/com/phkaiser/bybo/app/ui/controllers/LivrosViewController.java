// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package br.com.phkaiser.bybo.app.ui.controllers;

import br.com.phkaiser.bybo.core.domain.entity.Livro;
import br.com.phkaiser.bybo.core.domain.entity.StatusLivro;
import br.com.phkaiser.bybo.core.domain.repository.LivroRepository;
import br.com.phkaiser.bybo.persistence.repository.LivroRepositoryFileMsgPack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

/**
 * Controller para a view LivrosView.fxml.
 * Gerencia toda a interatividade da tela de gerenciamento de livros,
 * incluindo CRUD, filtro, seleção e validação.
 */
public class LivrosViewController {

    private final LivroRepository livroRepository = new LivroRepositoryFileMsgPack();

    //<editor-fold desc="Variáveis FXML">
    @FXML private TableView<Livro> livrosTableView;
    @FXML private TableColumn<Livro, String> colunaTitulo;
    @FXML private TableColumn<Livro, String> colunaAutor;
    @FXML private TableColumn<Livro, String> colunaIsbn;
    @FXML private TableColumn<Livro, Integer> colunaAno;
    @FXML private TableColumn<Livro, StatusLivro> colunaStatus;
    @FXML private TextField tituloField;
    @FXML private TextField autorField;
    @FXML private TextField isbnField;
    @FXML private TextField anoField;
    @FXML private TextField filtroField;
    @FXML private Button salvarButton;
    //</editor-fold>

    private ObservableList<Livro> masterData;
    private FilteredList<Livro> filteredData;

    @FXML
    public void initialize() {
        configurarColunasDaTabela();
        popularDadosIniciais();
        carregarLivros();
        configurarFiltroESelecao();
    }

    private void configurarColunasDaTabela() {
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colunaIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colunaAno.setCellValueFactory(new PropertyValueFactory<>("anoPublicacao"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        colunaStatus.setCellFactory(coluna -> new TableCell<>() {
            @Override
            protected void updateItem(StatusLivro status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status.toString());
                    if (status == StatusLivro.DISPONIVEL) {
                        setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                    }
                }
            }
        });
    }

    private void carregarLivros() {
        masterData = FXCollections.observableArrayList(livroRepository.buscarTodos());
        filteredData = new FilteredList<>(masterData, p -> true);
        livrosTableView.setItems(filteredData);
    }

    private void configurarFiltroESelecao() {
        filtroField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(livro -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                return livro.getTitulo().toLowerCase().contains(lowerCaseFilter) ||
                        livro.getAutor().toLowerCase().contains(lowerCaseFilter);
            });
        });

        livrosTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> exibirDetalhesDoLivro(newValue)
        );
    }

    private void exibirDetalhesDoLivro(Livro livro) {
        if (livro != null) {
            tituloField.setText(livro.getTitulo());
            autorField.setText(livro.getAutor());
            isbnField.setText(livro.getIsbn());
            anoField.setText(Integer.toString(livro.getAnoPublicacao()));
            salvarButton.setText("Atualizar");
        } else {
            handleLimparFormulario();
        }
    }

    @FXML
    private void handleSalvarLivro() {
        validateAndSave();
    }

    @FXML
    private void handleLimparFormulario() {
        livrosTableView.getSelectionModel().clearSelection();
        tituloField.clear();
        autorField.clear();
        isbnField.clear();
        anoField.clear();
        setFieldErrorStyle(tituloField, true);
        setFieldErrorStyle(autorField, true);
        setFieldErrorStyle(anoField, true);
        salvarButton.setText("Salvar");
        tituloField.requestFocus();
    }

    @FXML
    private void handleEmprestarLivro() {
        Livro livroSelecionado = livrosTableView.getSelectionModel().getSelectedItem();
        if (livroSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Por favor, selecione um livro para emprestar.");
            return;
        }
        if (livroSelecionado.getStatus() == StatusLivro.EMPRESTADO) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Operação Inválida", "Este livro já está emprestado.");
            return;
        }
        livroSelecionado.setStatus(StatusLivro.EMPRESTADO);
        livroRepository.salvar(livroSelecionado);
        livrosTableView.refresh();
    }

    @FXML
    private void handleDevolverLivro() {
        Livro livroSelecionado = livrosTableView.getSelectionModel().getSelectedItem();
        if (livroSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Por favor, selecione um livro para devolver.");
            return;
        }
        if (livroSelecionado.getStatus() == StatusLivro.DISPONIVEL) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Operação Inválida", "Este livro já está disponível.");
            return;
        }
        livroSelecionado.setStatus(StatusLivro.DISPONIVEL);
        livroRepository.salvar(livroSelecionado);
        livrosTableView.refresh();
    }

    @FXML
    private void handleExcluirLivro() {
        Livro livroSelecionado = livrosTableView.getSelectionModel().getSelectedItem();
        if (livroSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Por favor, selecione um livro para excluir.");
            return;
        }
        Optional<ButtonType> resultado = mostrarAlertaConfirmacao("Confirmar Exclusão", "Você tem certeza que deseja excluir o livro '" + livroSelecionado.getTitulo() + "'?");
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            livroRepository.deletarPorId(livroSelecionado.getId());
            masterData.remove(livroSelecionado);
        }
    }

    private boolean validateAndSave() {
        boolean tituloValido = !tituloField.getText().trim().isEmpty();
        boolean autorValido = !autorField.getText().trim().isEmpty();
        boolean anoValido = !anoField.getText().trim().isEmpty();
        int ano = 0;

        try {
            if (anoValido) ano = Integer.parseInt(anoField.getText());
        } catch (NumberFormatException e) {
            anoValido = false;
        }

        setFieldErrorStyle(tituloField, tituloValido);
        setFieldErrorStyle(autorField, autorValido);
        setFieldErrorStyle(anoField, anoValido);

        if (!tituloValido || !autorValido || !anoValido) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Inválidos", "Por favor, corrija os campos destacados.");
            return false;
        }

        Livro livroSelecionado = livrosTableView.getSelectionModel().getSelectedItem();
        if (livroSelecionado != null) {
            livroSelecionado.setTitulo(tituloField.getText());
            livroSelecionado.setAutor(autorField.getText());
            livroSelecionado.setIsbn(isbnField.getText());
            livroSelecionado.setAnoPublicacao(ano);
            livroRepository.salvar(livroSelecionado);
            livrosTableView.refresh();
        } else {
            Livro novoLivro = new Livro(tituloField.getText(), autorField.getText(), isbnField.getText(), ano);
            livroRepository.salvar(novoLivro);
            masterData.add(novoLivro);
        }
        handleLimparFormulario();
        return true;
    }

    private void setFieldErrorStyle(TextField field, boolean isValid) {
        if (isValid) field.getStyleClass().remove("error");
        else if (!field.getStyleClass().contains("error")) field.getStyleClass().add("error");
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private Optional<ButtonType> mostrarAlertaConfirmacao(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        return alert.showAndWait();
    }

    private void popularDadosIniciais() {
        if (livroRepository.buscarTodos().isEmpty()) {
            livroRepository.salvar(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "978-0618640157", 1954));
            livroRepository.salvar(new Livro("1984", "George Orwell", "978-0451524935", 1949));
        }
    }
}