// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13
package br.com.phkaiser.bybo.app.ui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

/**
 * Controller para o "Shell" principal da aplicação (MainView.fxml).
 * Responsável pela navegação entre as diferentes views.
 */
public class MainViewController {

    @FXML
    private AnchorPane contentPane;

    @FXML
    public void initialize() {
        // Carrega a view principal de livros ao iniciar a aplicação.
        handleNavegarLivros();
    }

    @FXML
    private void handleNavegarLivros() {
        loadView("LivrosView.fxml");
    }

    @FXML
    private void handleNavegarDashboard() {
        loadView("DashboardView.fxml");
    }

    @FXML
    private void handleSair() {
        Platform.exit();
    }

    /**
     * Carrega uma view FXML na área de conteúdo principal.
     * @param fxmlFileName O nome do arquivo FXML a ser carregado.
     */
    private void loadView(String fxmlFileName) {
        try {
            // Cria a transição de fade-out para o conteúdo atual
            FadeTransition fadeOut = new FadeTransition(Duration.millis(250), contentPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                try {
                    // Quando o fade-out termina, carrega o novo conteúdo
                    Node view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/br/com/phkaiser/bybo/app/ui/views/" + fxmlFileName)));
                    contentPane.getChildren().setAll(view);
                    AnchorPane.setTopAnchor(view, 0.0);
                    AnchorPane.setBottomAnchor(view, 0.0);
                    AnchorPane.setLeftAnchor(view, 0.0);
                    AnchorPane.setRightAnchor(view, 0.0);

                    // Cria a transição de fade-in para o novo conteúdo
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(250), contentPane);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }