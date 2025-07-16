// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package br.com.phkaiser.bybo.app.ui.controllers;

import br.com.phkaiser.bybo.core.domain.entity.Livro;
import br.com.phkaiser.bybo.core.domain.entity.StatusLivro;
import br.com.phkaiser.bybo.core.domain.repository.LivroRepository;
import br.com.phkaiser.bybo.persistence.repository.LivroRepositoryInMemory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.util.Optional;

/**
 * Controller para a MainView.fxml.
 * Esta classe contém a lógica para manipular os eventos da UI e interagir com a camada de negócio.
 */
public class MainViewController {

    private final LivroRepository livroRepository = new LivroRepositoryFileMsgPack();

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

    private ObservableList<Livro> livrosObservableList;

    @FXML
    public void initialize() {
        configurarColunasDaTabela();
        popularDadosIniciais();
        carregarLivros();
    }

    /**
     * Centraliza a configuração das colunas da tabela.
     */
    private void configurarColunasDaTabela() {
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colunaAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colunaIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colunaAno.setCellValueFactory(new PropertyValueFactory<>("anoPublicacao"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // *** A MÁGICA DA UI ACONTECE AQUI ***
        // Usamos uma CellFactory para customizar como as células da coluna de status são renderizadas.
        colunaStatus.setCellFactory(coluna -> new TableCell<>() {
            @Override
            protected void updateItem(StatusLivro status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setText(null);
                    setStyle(""); // Limpa qualquer estilo anterior
                } else {
                    setText(status.toString()); // Define o texto (DISPONIVEL ou EMPRESTADO)
                    // Define a cor do texto com base no status
                    if (status == StatusLivro.DISPONIVEL) {
                        // Usamos CSS inline para definir a cor. Verde para disponível.
                        setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold;");
                    } else {
                        // Vermelho para emprestado.
                        setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                    }
                }
            }
        });
    }

    private void carregarLivros() {
        livrosObservableList = FXCollections.observableArrayList(livroRepository.buscarTodos());
        livrosTableView.setItems(livrosObservableList);
    }

    @FXML
    private void handleAdicionarLivro() {
        if (tituloField.getText().isEmpty() || autorField.getText().isEmpty() || anoField.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validação", "Os campos Título, Autor e Ano são obrigatórios.");
            return;
        }
        try {
            Livro novoLivro = new Livro(tituloField.getText(), autorField.getText(), isbnField.getText(), Integer.parseInt(anoField.getText()));
            livroRepository.salvar(novoLivro);
            livrosObservableList.add(novoLivro);
            limparCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Formato", "O ano de publicação deve ser um número válido.");
        }
    }

    /**
     * NOVO: Chamado quando o botão "Emprestar Selecionado" é clicado.
     */
    @FXML
    private void handleEmprestarLivro() {
        Livro livroSelecionado = livrosTableView.getSelectionModel().getSelectedItem();

        if (livroSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Por favor, selecione um livro na tabela para emprestar.");
            return;
        }

        if (livroSelecionado.getStatus() == StatusLivro.EMPRESTADO) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Operação Inválida", "Este livro já está emprestado.");
            return;
        }

        livroSelecionado.setStatus(StatusLivro.EMPRESTADO);
        livroRepository.salvar(livroSelecionado);

        // Força a tabela a redesenhar a linha alterada, aplicando a nova cor da célula.
        livrosTableView.refresh();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Livro '" + livroSelecionado.getTitulo() + "' emprestado com sucesso!");
    }

    /**
     * NOVO: Chamado quando o botão "Devolver Selecionado" é clicado.
     */
    @FXML
    private void handleDevolverLivro() {
        Livro livroSelecionado = livrosTableView.getSelectionModel().getSelectedItem();

        if (livroSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Por favor, selecione um livro na tabela para devolver.");
            return;
        }

        if (livroSelecionado.getStatus() == StatusLivro.DISPONIVEL) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Operação Inválida", "Este livro já está disponível.");
            return;
        }

        livroSelecionado.setStatus(StatusLivro.DISPONIVEL);
        livroRepository.salvar(livroSelecionado);
        livrosTableView.refresh();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Livro '" + livroSelecionado.getTitulo() + "' devolvido com sucesso!");
    }

    /**
     * NOVO: Chamado quando o botão "Excluir Selecionado" é clicado.
     */
    @FXML
    private void handleExcluirLivro() {
        Livro livroSelecionado = livrosTableView.getSelectionModel().getSelectedItem();

        if (livroSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Por favor, selecione um livro na tabela para excluir.");
            return;
        }

        // Pergunta ao usuário se ele tem certeza, pois esta é uma ação destrutiva.
        Optional<ButtonType> resultado = mostrarAlertaConfirmacao(
                "Confirmar Exclusão",
                "Você tem certeza que deseja excluir o livro '" + livroSelecionado.getTitulo() + "'?\nEsta ação não pode ser desfeita."
        );

        // Se o usuário clicou em "OK"...
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            livroRepository.deletarPorId(livroSelecionado.getId());
            // Remove o item da lista observável, o que remove automaticamente da tabela.
            livrosObservableList.remove(livroSelecionado);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Livro excluído com sucesso.");
        }
    }

    private void limparCampos() {
        tituloField.clear();
        autorField.clear();
        isbnField.clear();
        anoField.clear();
    }

    /**
     * ATUALIZADO: Método genérico para mostrar alertas de diferentes tipos.
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * NOVO: Método específico para mostrar um diálogo de confirmação.
     */
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
            livroRepository.salvar(new Livro("Dom Quixote", "Miguel de Cervantes", "978-8535910030", 1605));
        }
    }
}