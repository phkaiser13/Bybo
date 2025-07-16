// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package br.com.phkaiser.bybo.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Classe principal e ponto de entrada para a aplicação gráfica ByBo com JavaFX.
 * Ela é responsável por carregar a view principal (FXML), a cena e exibir a janela (Stage).
 */
public class ByBoGraphicalApp extends Application {

    /**
     * O método start é o ponto de entrada principal para todas as aplicações JavaFX.
     * @param stage O "palco" principal da aplicação, fornecido pelo runtime do JavaFX. A janela.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader é usado para carregar a hierarquia de objetos a partir de um documento FXML.
        // Usamos getResource para encontrar o arquivo FXML no nosso classpath.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/br/com/phkaiser/bybo/app/ui/views/MainView.fxml")));

        // A Scene contém o 'root' (nossa view carregada) e define o tamanho da janela.
        Scene scene = new Scene(root, 1024, 768);

        // Carregamos nossa folha de estilos CSS e a aplicamos à cena.
        // É aqui que a mágica do design acontece.
        String css = Objects.requireNonNull(getClass().getResource("/br/com/phkaiser/bybo/app/ui/css/styles.css")).toExternalForm();
        scene.getStylesheets().add(css);

        // Configurações da janela principal (Stage).
        stage.setTitle("ByBo - Sistema de Gerenciamento de Biblioteca");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        // Exibe a janela para o usuário.
        stage.show();
    }

    /**
     * O método main é necessário para IDEs e para o deploy que não suportam
     * o lançamento direto de classes que estendem Application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}