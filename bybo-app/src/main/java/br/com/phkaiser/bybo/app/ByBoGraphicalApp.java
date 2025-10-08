// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package br.com.phkaiser.bybo.app;

import br.com.phkaiser.bybo.app.service.NotificationService;
import br.com.phkaiser.bybo.app.service.UpdateService;
import br.com.phkaiser.bybo.core.domain.entity.Campo;
import br.com.phkaiser.bybo.core.domain.entity.Tabela;
import br.com.phkaiser.bybo.core.domain.entity.TipoDado;
import br.com.phkaiser.bybo.persistence.repository.GenericRepositoryFileMsgPack;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ByBoGraphicalApp extends Application {

    private NotificationService notificationService;
    private UpdateService updateService;
    private GenericRepositoryFileMsgPack repository;
    private static HostServices hostServices;

    @Override
    public void init() throws Exception {
        super.init();
        this.notificationService = new NotificationService();
        this.updateService = new UpdateService();
        this.repository = new GenericRepositoryFileMsgPack();
        hostServices = getHostServices();
    }

    @Override
    public void start(Stage stage) throws IOException {
        notificationService.start();
        criarTabelaPadraoSeNecessario();

        // A verificação de atualização é assíncrona
        updateService.checkForUpdate().thenAccept(releaseUrl -> {
            if (releaseUrl != null) {
                // Se uma URL for retornada, significa que há uma atualização.
                // A notificação deve ser exibida na thread da UI.
                Platform.runLater(() -> notificarAtualizacaoDisponivel(releaseUrl));
            }
        });

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/br/com/phkaiser/bybo/app/ui/views/MainView.fxml")));
        Scene scene = new Scene(root, 1024, 768);
        String css = Objects.requireNonNull(getClass().getResource("/br/com/phkaiser/bybo/app/ui/css/styles.css")).toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("ByBo - Sistema de Gerenciamento Flexível");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/br/com/phkaiser/bybo/app/ui/appicons/bybo_icon.png")).toExternalForm()));
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    private void notificarAtualizacaoDisponivel(String url) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Atualização Disponível");
        alert.setHeaderText("Uma nova versão do ByBo está disponível!");
        alert.setContentText("Deseja ir para a página de download para atualizar agora?");

        ButtonType buttonTypeUpdate = new ButtonType("Atualizar Agora");
        ButtonType buttonTypeCancel = new ButtonType("Depois");

        alert.getButtonTypes().setAll(buttonTypeUpdate, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeUpdate) {
            hostServices.showDocument(url);
        }
    }

    @Override
    public void stop() throws Exception {
        notificationService.stop();
        super.stop();
    }

    private void criarTabelaPadraoSeNecessario() {
        if (repository.buscarTabelas().isEmpty()) {
            System.out.println("Nenhuma tabela encontrada. Criando modelo padrão...");

            List<Campo> camposPadrao = Arrays.asList(
                new Campo("Item", TipoDado.TEXTO),
                new Campo("Nome do Aluno", TipoDado.TEXTO),
                new Campo("Curso", TipoDado.TEXTO),
                new Campo("Professor", TipoDado.TEXTO),
                new Campo("Sala", TipoDado.TEXTO),
                new Campo("Bloco", TipoDado.TEXTO),
                new Campo("Data de Empréstimo", TipoDado.DATA),
                new Campo("Data de Retorno", TipoDado.DATA),
                new Campo("Status", TipoDado.LISTA_OPCOES, Arrays.asList("Emprestado", "Devolvido", "Manutenção"))
            );

            Tabela tabelaPadrao = new Tabela(
                "Empréstimos - Elétrica",
                "emprestimos_eletrica.mpack",
                camposPadrao
            );

            repository.salvarTabela(tabelaPadrao);
            System.out.println("Modelo 'Empréstimos - Elétrica' criado com sucesso.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}