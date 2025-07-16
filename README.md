<div align="center">
  <img src="https://raw.githubusercontent.com/phkaiser13/Bybo/main/.github/resources/Preview.png" alt="ByBo - Sistema de Gerenciamento de Biblioteca" width="700"/>
  <h1>📚 ByBo - Sistema de Gerenciamento de Biblioteca</h1>
  <p>
    <strong>Um projeto de demonstração de engenharia de software que une uma arquitetura robusta a uma GUI moderna e interativa com JavaFX.</strong>
  </p>
  <p>
    <img src="https://img.shields.io/badge/Java-17-blue?logo=openjdk&logoColor=white" alt="Java 17"/>
    <img src="https://img.shields.io/badge/Kotlin-1.9-blueviolet?logo=kotlin&logoColor=white" alt="Kotlin"/>
    <img src="https://img.shields.io/badge/JavaFX-17-orange?logo=openjfx&logoColor=white" alt="JavaFX 17"/>
    <img src="https://img.shields.io/badge/Maven-4.0-red?logo=apachemaven&logoColor=white" alt="Maven"/>
    <img src="https://img.shields.io/badge/Licença-MIT-green" alt="Licença MIT"/>
  </p>
  <p>
    <a href="https://github.com/phkaiser13/Bybo/releases/download/1.0-Def/ByBo-1.0.0.msi">
      <img src="https://img.shields.io/badge/Download-Windows-blue?logo=windows11&logoColor=white" alt="Download para Windows"/>
    </a>
  </p>
</div>

---

## ✨ Funcionalidades

*   🎨 **Interface Gráfica Moderna:** UI escura e elegante construída com JavaFX e estilizada com CSS.
*   ➡️ **Navegação Fluida:** Transições animadas e suaves entre as telas para uma melhor experiência de usuário.
*   💾 **Gerenciamento Completo (CRUD):** Adicione, edite e exclua livros de forma intuitiva.
*   🔍 **Busca Dinâmica:** Filtre e encontre livros na sua coleção em tempo real.
*   🔄 **Lógica de Empréstimo/Devolução:** Altere o status dos livros (disponível/emprestado) com um único clique.
*   📊 **Dashboard com Estatísticas:** Tenha uma visão geral da sua biblioteca com dados relevantes.
*   📦 **Persistência de Dados Eficiente:** Os dados da biblioteca são salvos localmente usando o formato MessagePack.
*   ⚠️ **Validação Visual:** Campos de formulário inválidos são destacados para facilitar a correção.

---

## 🛠️ Ferramentas e Tecnologias

<div align="center">
  <img src="https://img.shields.io/badge/Java-17-blue?logo=openjdk&logoColor=white" alt="Java 17" />
  <img src="https://img.shields.io/badge/Kotlin-1.9-blueviolet?logo=kotlin&logoColor=white" alt="Kotlin" />
  <img src="https://img.shields.io/badge/JavaFX-17-orange?logo=openjfx&logoColor=white" alt="JavaFX 17" />
  <img src="https://img.shields.io/badge/Maven-4.0-red?logo=apachemaven&logoColor=white" alt="Maven" />
  <img src="https://img.shields.io/badge/MessagePack-Serialization-pink?logo=messagepack&logoColor=white" alt="MessagePack" />
  <img src="https://img.shields.io/badge/CSS3-Styling-blue?logo=css3&logoColor=white" alt="CSS3" />
</div>

---

## 🏗️ Arquitetura Modular

O projeto é dividido em três módulos, seguindo as melhores práticas de design de software para garantir baixo acoplamento e alta coesão:

-   `bybo-core`: O coração do sistema. Contém as entidades de negócio e as interfaces dos repositórios.
-   `bybo-persistence`: A camada de dados. Implementa o repositório para ler e escrever os dados da aplicação.
-   `bybo-app`: A camada de apresentação. Contém a lógica da interface gráfica e a interação com o usuário.

<div align="center">
  <img src="https://www.plantuml.com/plantuml/svg/SoWkIImgAStDuNBCoajB2L9DBb8mG5QgoS992G00" alt="Diagrama de Arquitetura"/>
</div>

---

## 🚀 Como Executar

Você precisará do **JDK 17 (ou superior)** e do **Apache Maven** instalados.

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/phkaiser13/Bybo.git
    cd Bybo
    ```

2.  **Execute a aplicação (Modo de Desenvolvimento):**
    ```bash
    mvn clean javafx:run
    ```

3.  **Gerando o Pacote Final:**
    ```bash
    # 1. Empacota a aplicação
    mvn clean package

    # 2. Executa o arquivo .jar gerado
    java -jar bybo-app/target/bybo-app-1.0.0-SNAPSHOT.jar
    ```

---

## 📄 Licença

Distribuído sob a licença MIT. Veja `LICENSE` para mais informações.

<p align="center">
  <br>
  Feito por Pedro Henrique Garcia
  <br>
  <strong><a href="https://github.com/phkaiser13">GitHub</a></strong>
</p>
