package br.com.phkaiser.bybo.app.service;

import br.com.phkaiser.bybo.core.domain.entity.Item;
import br.com.phkaiser.bybo.core.domain.entity.Tabela;
import br.com.phkaiser.bybo.core.domain.entity.TipoDado;
import br.com.phkaiser.bybo.persistence.repository.GenericRepositoryFileMsgPack;
import javafx.application.Platform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificationService {

    private final GenericRepositoryFileMsgPack repository;
    private ScheduledExecutorService scheduler;
    private TrayIcon trayIcon;

    // O nome do campo que acionará as notificações.
    private static final String CAMPO_DATA_NOTIFICAVEL = "Data de Retorno";

    public NotificationService() {
        this.repository = new GenericRepositoryFileMsgPack();
    }

    public void start() {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray não é suportado, o serviço de notificação não será iniciado.");
            return;
        }

        try {
            // Garante que o AWT não feche a aplicação JavaFX
            Platform.setImplicitExit(false);

            // Cria o ícone da bandeja
            URL imageUrl = getClass().getResource("/br/com/phkaiser/bybo/app/ui/appicons/bybo_icon.png");
            Image image = ImageIO.read(imageUrl);
            this.trayIcon = new TrayIcon(image, "ByBo Notificações");
            trayIcon.setImageAutoSize(true);
            SystemTray.getSystemTray().add(trayIcon);

            // Inicia o agendador
            this.scheduler = Executors.newSingleThreadScheduledExecutor();
            // Verifica a cada hora. Para testes, pode-se diminuir o tempo.
            scheduler.scheduleAtFixedRate(this::verificarPrazos, 0, 1, TimeUnit.HOURS);

        } catch (AWTException | IOException e) {
            System.err.println("Erro ao iniciar o serviço de notificação: " + e.getMessage());
        }
    }

    public void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
        // Remove o ícone da bandeja ao fechar
        if (trayIcon != null) {
            SystemTray.getSystemTray().remove(trayIcon);
        }
        // Permite que a aplicação feche normalmente
        Platform.setImplicitExit(true);
    }

    private void verificarPrazos() {
        List<Tabela> tabelas = repository.buscarTabelas();
        LocalDate hoje = LocalDate.now();

        for (Tabela tabela : tabelas) {
            tabela.getCampos().stream()
                .filter(campo -> campo.getTipo() == TipoDado.DATA && CAMPO_DATA_NOTIFICAVEL.equalsIgnoreCase(campo.getNome()))
                .findFirst()
                .ifPresent(campoData -> {
                    List<Item> items = repository.buscarTodos(tabela.getId());
                    for (Item item : items) {
                        Object valorDataObj = item.getValores().get(campoData.getNome());
                        if (valorDataObj != null) {
                            try {
                                LocalDate dataRetorno = LocalDate.parse(valorDataObj.toString());
                                long diasRestantes = ChronoUnit.DAYS.between(hoje, dataRetorno);

                                if (diasRestantes <= 1) { // Notifica se for hoje, amanhã ou vencido
                                    String mensagem = construirMensagem(item, diasRestantes);
                                    exibirNotificacao("Alerta de Prazo - ByBo", mensagem);
                                }
                            } catch (Exception e) {
                                // Ignora item se a data for inválida
                            }
                        }
                    }
                });
        }
    }

    private String construirMensagem(Item item, long dias) {
        // Tenta encontrar um campo "nome" ou "item" para identificar o registro
        String nomeItem = item.getValores().entrySet().stream()
                .filter(e -> e.getKey().toLowerCase().contains("item") || e.getKey().toLowerCase().contains("nome"))
                .map(e -> e.getValue().toString())
                .findFirst()
                .orElse("ID: " + item.getId().toString().substring(0, 8));

        if (dias < 0) {
            return String.format("O item '%s' está vencido há %d dia(s)!", nomeItem, Math.abs(dias));
        } else if (dias == 0) {
            return String.format("O item '%s' vence hoje!", nomeItem);
        } else {
            return String.format("O item '%s' vence amanhã!", nomeItem);
        }
    }

    private void exibirNotificacao(String titulo, String mensagem) {
        // Garante que a notificação seja exibida na thread de UI do AWT
        Platform.runLater(() -> {
            trayIcon.displayMessage(titulo, mensagem, TrayIcon.MessageType.INFO);
        });
    }
}