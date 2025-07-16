package br.com.phkaiser.bybo.app;

/**
 * Classe lançadora para contornar problemas de carregamento de módulos JavaFX
 * ao empacotar a aplicação em um JAR executável.
 */
public class Launcher {
    public static void main(String[] args) {
        ByBoGraphicalApp.main(args);
    }
}