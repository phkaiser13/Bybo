package br.com.phkaiser.bybo.app.ui.controllers;

import br.com.phkaiser.bybo.core.domain.entity.Tabela;
import br.com.phkaiser.bybo.persistence.repository.GenericRepositoryFileMsgPack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.List;

public class MainViewController {

    @FXML
    private AnchorPane contentPane; // A área onde as views serão carregadas

    @FXML
    private VBox navigationVBox; // O VBox que contém os botões de navegação

    private GenericRepositoryFileMsgPack repository;

    @FXML
    public void initialize() {
        this.repository = new GenericRepositoryFileMsgPack();
        carregarTabelasNaNav();
        // Opcional: carregar uma view padrão ao iniciar, como o dashboard.
        handleNavegarDashboard(null);
    }

    private void carregarTabelasNaNav() {
        List<Tabela> tabelas = repository.buscarTabelas();

        // O índice 0 é o botão "Gerenciar Tabelas", inserimos as novas tabelas depois dele.
        int buttonIndex = 1; 
        for (Tabela tabela : tabelas) {
            Button navButton = new Button(tabela.getNome());
            navButton.setMaxWidth(Double.MAX_VALUE);
            navButton.getStyleClass().add("nav-button");
            navButton.setGraphic(new FontIcon("fas-table"));
            navButton.setOnAction(event -> navegarParaTabela(tabela));
            navigationVBox.getChildren().add(buttonIndex++, navButton);
        }
    }
    
    private void navegarParaTabela(Tabela tabela) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/phkaiser/bybo/app/ui/views/GenericDataView.fxml"));
            Node view = loader.load();

            GenericDataViewController controller = loader.getController();
            controller.carregarDadosDaTabela(tabela);

            setView(view);
        } catch (IOException e) {
            e.printStackTrace();
            // Tratar o erro (ex: mostrar um alerta)
        }
    }
    
    @FXML
    void handleNavegarSchemaEditor(ActionEvent event) {
        carregarView("/br/com/phkaiser/bybo/app/ui/views/SchemaEditorView.fxml");
    }

    @FXML
    void handleNavegarDashboard(ActionEvent event) {
        carregarView("/br/com/phkaiser/bybo/app/ui/views/DashboardView.fxml");
    }

    @FXML
    void handleSair(ActionEvent event) {
        Platform.exit();
    }

    private void carregarView(String fxmlPath) {
        try {
            Node view = FXMLLoader.load(getClass().getResource(fxmlPath));
            setView(view);
        } catch (IOException e) {
            e.printStackTrace();
            // Tratar o erro (ex: mostrar um alerta)
        }
    }

    private void setView(Node node) {
        contentPane.getChildren().setAll(node);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
    }
}