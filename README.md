<!--
// 2025
// By Pedro henrique garcia.
// Github/gitlab: Phkaiser13
-->

# 📚 ByBo - Sistema de Gerenciamento de Biblioteca

Bem-vindo ao ByBo! Este não é apenas um sistema de biblioteca funcional, mas um projeto de demonstração de engenharia de software que combina uma arquitetura robusta e modular com uma interface de usuário (GUI) moderna, bonita e interativa construída com JavaFX.

O objetivo do ByBo é ser uma ferramenta de gerenciamento de livros completa, elegante e um prazer de se usar.

![Screenshot do ByBo](https://i.imgur.com/YOUR_SCREENSHOT_ID.png)
*(Dica: Tire um screenshot da sua aplicação, faça o upload em um site como o [Imgur](https://imgur.com/upload) e substitua o link acima para exibir a imagem aqui!)*

---

## ✨ Funcionalidades

*   **Interface Gráfica Moderna:** UI escura e elegante construída com JavaFX e estilizada com CSS.
*   **Navegação Fluida:** Barra de navegação lateral com transições animadas suaves entre as telas.
*   **Gerenciamento Completo (CRUD):** Adicione, edite e exclua livros de forma intuitiva.
*   **Busca Dinâmica:** Filtre a lista de livros em tempo real enquanto você digita.
*   **Lógica de Empréstimo/Devolução:** Altere o status dos livros com um clique, com feedback visual imediato.
*   **Dashboard com Estatísticas:** Uma visão geral da biblioteca com o total de livros, disponíveis e emprestados.
*   **Persistência de Dados Eficiente:** Os dados são salvos entre as sessões usando o formato binário **MessagePack**, que é mais rápido e compacto que JSON.
*   **Validação Visual:** Campos de formulário inválidos são destacados para guiar o usuário.

---

## 🛠️ Ferramentas e Tecnologias

O ByBo foi construído com um conjunto de ferramentas modernas e padrões da indústria:

*   **Linguagens:** **Java 17** (predominante) & **Kotlin** (demonstrando interoperabilidade no módulo `core`).
*   **Framework UI:** **JavaFX 17** para a construção de toda a interface gráfica.
*   **Build & Dependências:** **Apache Maven** para gerenciar o projeto multi-módulo e suas bibliotecas.
*   **Persistência:** **MessagePack** com a biblioteca Jackson para serialização de dados eficiente.
*   **Ícones:** **Ikonli** para ícones vetoriais nítidos e facilmente estilizáveis via CSS.

---

## 🏗️ Arquitetura Modular

O projeto é dividido em três módulos independentes, seguindo as melhores práticas de design de software para garantir baixo acoplamento e alta coesão:

*   **`bybo-core`**: O coração do sistema. Contém as entidades de negócio (`Livro`, `StatusLivro`) e as interfaces dos repositórios. Não possui dependências de UI ou persistência.
*   **`bybo-persistence`**: A camada de dados. Contém a implementação concreta do repositório (`LivroRepositoryFileMsgPack`), responsável por ler e escrever os dados no disco.
*   **`bybo-app`**: A camada de apresentação. Contém toda a lógica da interface gráfica (views FXML, controllers e CSS), orquestrando a interação do usuário com a camada de negócio.

---

## 🚀 Como Executar

Você precisará do **JDK 17** (ou superior) e do **Apache Maven** instalados em seu sistema.

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/seu-repositorio.git
    cd seu-repositorio
    ```

2.  **Execute a aplicação:**

    *   **Modo de Desenvolvimento (recomendado para testar):**
        Este comando compila e inicia a aplicação rapidamente.
        ```bash
        mvn clean javafx:run
        ```

    *   **Gerando o Pacote Final:**
        Este comando cria um arquivo `.jar` "tudo-em-um" na pasta `bybo-app/target/`, que pode ser distribuído e executado em qualquer lugar.
        ```bash
        # 1. Empacota a aplicação
        mvn clean package

        # 2. Executa o arquivo .jar gerado
        java -jar bybo-app/target/bybo-app-1.0.0-SNAPSHOT.jar
        ```

---

## 📄 Licença

Distribuído sob a licença MIT. Veja `LICENSE` para mais informações.

---
<p align="center">
  // 2025<br>
  // By Pedro henrique garcia.<br>
  // Github/gitlab: Phkaiser13
</p>
