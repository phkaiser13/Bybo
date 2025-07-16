// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13

package br.com.phkaiser.bybo.app;

import br.com.phkaiser.bybo.core.domain.entity.Livro;
import br.com.phkaiser.bybo.core.domain.entity.StatusLivro;
import br.com.phkaiser.bybo.core.domain.repository.LivroRepository;
import br.com.phkaiser.bybo.persistence.repository.LivroRepositoryInMemory;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import br.com.phkaiser.bybo.persistence.repository.LivroRepositoryFileMsgPack;

import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Classe principal da aplicação ByBo.
 * Ponto de entrada que controla o fluxo do programa e a interação com o usuário.
 */
public class ByBoApplication {

    private static final LivroRepository livroRepository = new LivroRepositoryFileMsgPack();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        popularDadosIniciais();

        while (true) {
            exibirMenuPrincipal();
            int opcao = lerOpcaoDoUsuario();

            switch (opcao) {
                case 1:
                    adicionarNovoLivro();
                    break;
                case 2:
                    gerenciarMenuDeBusca();
                    continue;
                case 3:
                    listarTodosOsLivros();
                    break;
                // *** ALTERAÇÃO AQUI: Ativando os novos casos ***
                case 4:
                    registrarEmprestimo();
                    break;
                case 5:
                    registrarDevolucao();
                    break;
                case 0:
                    System.out.println(ansi().fg(Ansi.Color.YELLOW).a("\nObrigado por usar o ByBo! Até a próxima.").reset());
                    AnsiConsole.systemUninstall();
                    scanner.close();
                    return;
                default:
                    System.out.println(ansi().fg(Ansi.Color.RED).a("Opção inválida! Por favor, tente novamente.").reset());
                    break;
            }
            pressioneEnterParaContinuar();
        }
    }

    // --- MÉTODOS DE MENU ---

    private static void exibirMenuPrincipal() {
        System.out.print(ansi().eraseScreen());
        System.out.println(ansi().fg(Ansi.Color.CYAN).a("╔══════════════════════════════════════════════════════╗").reset());
        System.out.println(ansi().fg(Ansi.Color.CYAN).a("║").fg(Ansi.Color.WHITE).bold().a("      Bem-vindo ao ByBo - Sistema de Biblioteca     ").fg(Ansi.Color.CYAN).a("║").reset());
        System.out.println(ansi().fg(Ansi.Color.CYAN).a("╚══════════════════════════════════════════════════════╝").reset());
        System.out.println();
        System.out.println(ansi().fg(Ansi.Color.YELLOW).a("  [1]").reset().a(" Adicionar Novo Livro"));
        System.out.println(ansi().fg(Ansi.Color.YELLOW).a("  [2]").reset().a(" Buscar Livros"));
        System.out.println(ansi().fg(Ansi.Color.YELLOW).a("  [3]").reset().a(" Listar Todos os Livros"));
        // *** ALTERAÇÃO AQUI: Removendo o "(Em breve)" ***
        System.out.println(ansi().fg(Ansi.Color.YELLOW).a("  [4]").reset().a(" Registrar Empréstimo"));
        System.out.println(ansi().fg(Ansi.Color.YELLOW).a("  [5]").reset().a(" Registrar Devolução"));
        System.out.println();
        System.out.println(ansi().fg(Ansi.Color.RED).a("  [0]").reset().a(" Sair do Sistema"));
        System.out.println();
        System.out.print(ansi().bold().a("Escolha uma opção: ").reset());
    }

    // ... (o resto dos métodos de menu e busca permanecem os mesmos) ...
    private static void exibirMenuBusca() {
        System.out.print(ansi().eraseScreen());
        System.out.println(ansi().fg(Ansi.Color.CYAN).a("╔══════════════════════════════════════════════════════╗").reset());
        System.out.println(ansi().fg(Ansi.Color.CYAN).a("║").fg(Ansi.Color.WHITE).bold().a("                     Menu de Busca                    ").fg(Ansi.Color.CYAN).a("║").reset());
        System.out.println(ansi().fg(Ansi.Color.CYAN).a("╚══════════════════════════════════════════════════════╝").reset());
        System.out.println();
        System.out.println(ansi().fg(Ansi.Color.YELLOW).a("  [1]").reset().a(" Buscar por Título"));
        System.out.println(ansi().fg(Ansi.Color.YELLOW).a("  [2]").reset().a(" Buscar por Autor"));
        System.out.println();
        System.out.println(ansi().fg(Ansi.Color.RED).a("  [0]").reset().a(" Voltar ao Menu Principal"));
        System.out.println();
        System.out.print(ansi().bold().a("Escolha uma opção de busca: ").reset());
    }


    // --- MÉTODOS DE LÓGICA E FLUXO ---

    private static void gerenciarMenuDeBusca() {
        while (true) {
            exibirMenuBusca();
            int opcao = lerOpcaoDoUsuario();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    buscarLivroPorAutor();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(ansi().fg(Ansi.Color.RED).a("Opção inválida! Por favor, tente novamente.").reset());
                    break;
            }
            pressioneEnterParaContinuar();
        }
    }

    /**
     * NOVO MÉTODO: Orquestra a lógica para registrar o empréstimo de um livro.
     */
    private static void registrarEmprestimo() {
        System.out.println(ansi().fg(Ansi.Color.CYAN).a("\n--- Registrar Empréstimo ---").reset());
        System.out.println("Você pode ver a lista de IDs na opção [3] do menu principal.");
        System.out.print("Digite o ID do livro que deseja emprestar: ");

        // O método 'buscarLivroPorIdInputUsuario' já lida com ID inválido ou não encontrado.
        Optional<Livro> livroOpt = buscarLivroPorIdInputUsuario();

        // Usamos ifPresent para executar um código apenas se o livro foi encontrado.
        livroOpt.ifPresent(livro -> {
            if (livro.getStatus() == StatusLivro.DISPONIVEL) {
                livro.setStatus(StatusLivro.EMPRESTADO);
                livroRepository.salvar(livro); // Salva a alteração no repositório.
                System.out.println(ansi().fg(Ansi.Color.GREEN).bold().a("\nEmpréstimo do livro '").a(livro.getTitulo()).a("' registrado com sucesso!").reset());
            } else {
                // O livro existe, mas já está emprestado.
                System.out.println(ansi().fg(Ansi.Color.RED).a("\nErro: O livro '").a(livro.getTitulo()).a("' já está emprestado.").reset());
            }
        });
    }

    /**
     * NOVO MÉTODO: Orquestra a lógica para registrar a devolução de um livro.
     */
    private static void registrarDevolucao() {
        System.out.println(ansi().fg(Ansi.Color.CYAN).a("\n--- Registrar Devolução ---").reset());
        System.out.print("Digite o ID do livro que está sendo devolvido: ");

        Optional<Livro> livroOpt = buscarLivroPorIdInputUsuario();

        livroOpt.ifPresent(livro -> {
            if (livro.getStatus() == StatusLivro.EMPRESTADO) {
                livro.setStatus(StatusLivro.DISPONIVEL);
                livroRepository.salvar(livro);
                System.out.println(ansi().fg(Ansi.Color.GREEN).bold().a("\nDevolução do livro '").a(livro.getTitulo()).a("' registrada com sucesso!").reset());
            } else {
                // O livro existe, mas já estava disponível.
                System.out.println(ansi().fg(Ansi.Color.RED).a("\nErro: O livro '").a(livro.getTitulo()).a("' já constava como disponível.").reset());
            }
        });
    }


    // --- MÉTODOS UTILITÁRIOS (Helpers) ---

    /**
     * NOVO MÉTODO (Refatorado): Pede um ID ao usuário, valida e busca no repositório.
     * Centraliza a lógica de obter um livro por ID, tratando erros de formato e de "não encontrado".
     * @return um Optional<Livro> contendo o livro se encontrado, ou vazio caso contrário.
     */
    private static Optional<Livro> buscarLivroPorIdInputUsuario() {
        String idString = scanner.nextLine();
        UUID id;

        try {
            // Tenta converter a String do usuário para um objeto UUID.
            id = UUID.fromString(idString);
        } catch (IllegalArgumentException e) {
            // Se a String não for um UUID válido, exibe um erro.
            System.out.println(ansi().fg(Ansi.Color.RED).a("\nFormato de ID inválido.").reset());
            return Optional.empty();
        }

        Optional<Livro> livroOpt = livroRepository.buscarPorId(id);
        if (livroOpt.isEmpty()) {
            // Se o UUID é válido mas não corresponde a nenhum livro, exibe um erro.
            System.out.println(ansi().fg(Ansi.Color.RED).a("\nNenhum livro encontrado com o ID fornecido.").reset());
        }

        return livroOpt;
    }

    // ... (todos os outros métodos como adicionarNovoLivro, listarTodosOsLivros, etc., permanecem aqui sem alterações) ...
    private static void adicionarNovoLivro() {
        System.out.println(ansi().fg(Ansi.Color.CYAN).a("\n--- Adicionar Novo Livro ---").reset());
        System.out.print("Digite o título: ");
        String titulo = scanner.nextLine();
        System.out.print("Digite o autor: ");
        String autor = scanner.nextLine();
        System.out.print("Digite o ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Digite o ano de publicação: ");
        int ano = lerOpcaoDoUsuario();

        Livro novoLivro = new Livro(titulo, autor, isbn, ano);
        livroRepository.salvar(novoLivro);

        System.out.println(ansi().fg(Ansi.Color.GREEN).bold().a("\nLivro adicionado com sucesso!").reset());
    }

    private static void listarTodosOsLivros() {
        List<Livro> livros = livroRepository.buscarTodos();
        exibirListaDeLivros(livros, "--- Lista de Todos os Livros ---");
    }

    private static void buscarLivroPorTitulo() {
        System.out.println(ansi().fg(Ansi.Color.CYAN).a("\n--- Busca por Título ---").reset());
        System.out.print("Digite o termo para buscar no título: ");
        String termo = scanner.nextLine();
        List<Livro> livrosEncontrados = livroRepository.buscarPorTitulo(termo);
        exibirListaDeLivros(livrosEncontrados, "--- Resultados da Busca por Título ---");
    }

    private static void buscarLivroPorAutor() {
        System.out.println(ansi().fg(Ansi.Color.CYAN).a("\n--- Busca por Autor ---").reset());
        System.out.print("Digite o termo para buscar no autor: ");
        String termo = scanner.nextLine();
        List<Livro> livrosEncontrados = livroRepository.buscarPorAutor(termo);
        exibirListaDeLivros(livrosEncontrados, "--- Resultados da Busca por Autor ---");
    }

    private static void exibirListaDeLivros(List<Livro> livros, String cabecalho) {
        System.out.println(ansi().fg(Ansi.Color.CYAN).a("\n" + cabecalho).reset());

        if (livros.isEmpty()) {
            System.out.println(ansi().fg(Ansi.Color.YELLOW).a("Nenhum livro encontrado.").reset());
            return;
        }

        String header = String.format("%-38s %-25s %-20s %-15s", "ID", "TÍTULO", "AUTOR", "STATUS");
        System.out.println(ansi().bold().a(header).reset());
        System.out.println(ansi().a("-".repeat(header.length() + 5)).reset());

        for (Livro livro : livros) {
            Ansi.Color statusColor = livro.getStatus() == br.com.phkaiser.bybo.core.domain.entity.StatusLivro.DISPONIVEL ? Ansi.Color.GREEN : Ansi.Color.RED;
            String tituloCurto = livro.getTitulo().length() > 23 ? livro.getTitulo().substring(0, 22) + "..." : livro.getTitulo();
            String autorCurto = livro.getAutor().length() > 18 ? livro.getAutor().substring(0, 17) + "..." : livro.getAutor();

            String linha = String.format("%-38s %-25s %-20s ", livro.getId(), tituloCurto, autorCurto);
            System.out.print(linha);
            System.out.println(ansi().fg(statusColor).a(String.format("%-15s", livro.getStatus())).reset());
        }
    }

    private static int lerOpcaoDoUsuario() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.next();
            return -1;
        } finally {
            if(scanner.hasNextLine()) {
                scanner.nextLine();
            }
        }
    }

    private static void pressioneEnterParaContinuar() {
        System.out.println(ansi().fg(Ansi.Color.GREEN).a("\nPressione [ENTER] para continuar...").reset());
        scanner.nextLine();
    }

    private static void popularDadosIniciais() {
        livroRepository.salvar(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "978-0618640157", 1954));
        livroRepository.salvar(new Livro("1984", "George Orwell", "978-0451524935", 1949));
        livroRepository.salvar(new Livro("Dom Quixote", "Miguel de Cervantes", "978-8535910030", 1605));
        livroRepository.salvar(new Livro("O Guia do Mochileiro das Galáxias", "Douglas Adams", "978-8575422465", 1979));
    }
}