// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package br.com.phkaiser.bybo.app.ui.controllers;

import br.com.phkaiser.bybo.core.domain.entity.Livro;
import br.com.phkaiser.bybo.core.domain.entity.StatusLivro;
import br.com.phkaiser.bybo.core.domain.repository.LivroRepository;
import br.com.phkaiser.bybo.persistence.repository.LivroRepositoryFileMsgPack;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class DashboardViewController {

    private final LivroRepository livroRepository = new LivroRepositoryFileMsgPack();

    @FXML private Label totalLivrosLabel;
    @FXML private Label disponiveisLabel;
    @FXML private Label emprestadosLabel;

    @FXML
    public void initialize() {
        carregarEstatisticas();
    }

    private void carregarEstatisticas() {
        List<Livro> todosOsLivros = livroRepository.buscarTodos();

        long total = todosOsLivros.size();
        long disponiveis = todosOsLivros.stream().filter(livro -> livro.getStatus() == StatusLivro.DISPONIVEL).count();
        long emprestados = total - disponiveis;

        totalLivrosLabel.setText(String.valueOf(total));
        disponiveisLabel.setText(String.valueOf(disponiveis));
        emprestadosLabel.setText(String.valueOf(emprestados));
    }
}