package br.com.phkaiser.bybo.app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class UpdateService {

    private static final String GITHUB_REPO = "phkaiser/bybo"; // Substituir pelo seu repositório
    private static final String LATEST_RELEASE_URL = "https://api.github.com/repos/" + GITHUB_REPO + "/releases/latest";
    private static final String RELEASE_INFO_ASSET_NAME = "release-info.json";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public UpdateService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Verifica se há uma nova atualização disponível.
     * @return um CompletableFuture que conterá a URL da página de release se uma atualização estiver disponível, ou null caso contrário.
     */
    public CompletableFuture<String> checkForUpdate() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // 1. Obter a versão atual da aplicação
                String currentVersion = getCurrentAppVersion();
                if (currentVersion == null) {
                    System.err.println("Não foi possível determinar a versão atual da aplicação.");
                    return null;
                }
                System.out.println("Versão atual: " + currentVersion);

                // 2. Buscar dados da última release no GitHub
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(LATEST_RELEASE_URL))
                        .header("Accept", "application/vnd.github.v3+json")
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    System.err.println("Erro ao buscar releases: " + response.statusCode());
                    return null;
                }

                // 3. Encontrar a URL do release-info.json
                JsonNode root = objectMapper.readTree(response.body());
                String releaseInfoUrl = findAssetUrl(root);

                if (releaseInfoUrl == null) {
                    System.err.println("Não foi possível encontrar o arquivo 'release-info.json' na última release.");
                    return null;
                }

                // 4. Baixar e ler o release-info.json
                HttpRequest assetRequest = HttpRequest.newBuilder().uri(URI.create(releaseInfoUrl)).build();
                HttpResponse<String> assetResponse = httpClient.send(assetRequest, HttpResponse.BodyHandlers.ofString());
                JsonNode releaseInfo = objectMapper.readTree(assetResponse.body());
                String latestVersion = releaseInfo.get("version").asText();
                System.out.println("Versão mais recente encontrada: " + latestVersion);

                // 5. Comparar as versões
                if (!currentVersion.equals(latestVersion)) {
                    System.out.println("Nova atualização disponível!");
                    return releaseInfo.get("url").asText(); // Retorna a URL da página de download
                } else {
                    System.out.println("A aplicação já está na versão mais recente.");
                    return null;
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    private String findAssetUrl(JsonNode releaseNode) {
        if (releaseNode.has("assets")) {
            for (JsonNode asset : releaseNode.get("assets")) {
                if (RELEASE_INFO_ASSET_NAME.equals(asset.get("name").asText())) {
                    return asset.get("browser_download_url").asText();
                }
            }
        }
        return null;
    }

    private String getCurrentAppVersion() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("META-INF/maven/br.com.phkaiser.bybo/bybo-app/pom.properties")) {
            if (input == null) {
                // Isso pode acontecer durante o desenvolvimento na IDE. Retornar uma versão de dev.
                return "0.0.0-DEV"; 
            }
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("version");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}