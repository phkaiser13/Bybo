<div align="center">
  <img src="https://raw.githubusercontent.com/phkaiser13/Bybo/main/.github/resources/Preview.png" alt="ByBo - Sistema de Gerenciamento de Biblioteca" width="700"/>
  <br>
  <h1>📚 ByBo - Sistema de Gerenciamento de Biblioteca</h1>
  <p>
    <strong>Um projeto de demonstração de engenharia de software que une uma arquitetura robusta a uma GUI moderna e interativa com JavaFX.</strong>
  </p>
  
  <!-- Badges de Tecnologia -->
  <p>
    <img src="https://img.shields.io/badge/Java-17-blue?logo=openjdk&logoColor=white" alt="Java 17"/>
    <img src="https://img.shields.io/badge/Kotlin-1.9-blueviolet?logo=kotlin&logoColor=white" alt="Kotlin"/>
    <img src="https://img.shields.io/badge/JavaFX-17-orange?logo=openjfx&logoColor=white" alt="JavaFX 17"/>
    <img src="https://img.shields.io/badge/Maven-4.0-red?logo=apachemaven&logoColor=white" alt="Maven"/>
    <img src="https://img.shields.io/badge/Licença-MIT-green" alt="Licença MIT"/>
  </p>
</div>

---

### 🚀 Para Usuários: Baixe e Instale

<div align="center">
  <p>Clique no botão abaixo para baixar o instalador para Windows.</p>
  <a href="https://github.com/phkaiser13/Bybo/releases/download/1.0-Def/ByBo-1.0.0.msi" title="Baixar para Windows">
    <img src="https://img.shields.io/badge/Baixar_para_Windows-0078D6?style=for-the-badge&logo=windows11&logoColor=white" alt="Download para Windows"/>
  </a>
</div>

---

### ✨ Funcionalidades Principais

<table width="100%">
  <tr>
    <td width="50%" valign="top">
      <img src="https://raw.githubusercontent.com/phkaiser13/Bybo/main/.github/resources/Preview.png" alt="Interface Moderna" width="100%"/>
    </td>
    <td width="50%" valign="top">
      <ul>
        <li>🎨 <strong>Interface Gráfica Moderna:</strong> UI escura e elegante construída com JavaFX e estilizada com CSS.</li>
        <li>➡️ <strong>Navegação Fluida:</strong> Transições animadas e suaves entre as telas para uma melhor experiência de usuário.</li>
        <li>💾 <strong>Gerenciamento Completo (CRUD):</strong> Adicione, edite e exclua livros de forma intuitiva.</li>
        <li>🔍 <strong>Busca Dinâmica:</strong> Filtre e encontre livros na sua coleção em tempo real.</li>
        <li>🔄 <strong>Lógica de Empréstimo/Devolução:</strong> Altere o status dos livros (disponível/emprestado) com um único clique.</li>
        <li>📊 <strong>Dashboard com Estatísticas:</strong> Tenha uma visão geral da sua biblioteca com dados relevantes.</li>
        <li>📦 <strong>Persistência de Dados Eficiente:</strong> Os dados da biblioteca são salvos localmente usando o formato MessagePack.</li>
        <li>⚠️ <strong>Validação Visual:</strong> Campos de formulário inválidos são destacados para facilitar a correção.</li>
      </ul>
    </td>
  </tr>
</table>

---

### 🛠️ Ferramentas e Tecnologias

<div align="center">
  <img src="https://img.shields.io/badge/Java-17-blue?logo=openjdk&logoColor=white" alt="Java 17" />
  <img src="https://img.shields.io/badge/Kotlin-1.9-blueviolet?logo=kotlin&logoColor=white" alt="Kotlin" />
  <img src="https://img.shields.io/badge/JavaFX-17-orange?logo=openjfx&logoColor=white" alt="JavaFX 17" />
  <img src="https://img.shields.io/badge/Maven-4.0-red?logo=apachemaven&logoColor=white" alt="Maven" />
  <img src="https://img.shields.io/badge/MessagePack-Serialization-pink?logo=messagepack&logoColor=white" alt="MessagePack" />
  <img src="https://img.shields.io/badge/CSS3-Styling-blue?logo=css3&logoColor=white" alt="CSS3" />
</div>

---

### 🏗️ Arquitetura Modular

O projeto é dividido em três módulos, seguindo as melhores práticas de design de software para garantir baixo acoplamento e alta coesão:

-   `bybo-core`: O coração do sistema. Contém as entidades de negócio e as interfaces dos repositórios.
-   `bybo-persistence`: A camada de dados. Implementa o repositório para ler e escrever os dados da aplicação.
-   `bybo-app`: A camada de apresentação. Contém a lógica da interface gráfica e a interação com o usuário.

---

### 👨‍💻 Para Desenvolvedores: Executando o Projeto

Para compilar e executar o projeto localmente, você precisará do **JDK 17 (ou superior)** e do **Apache Maven** instalados.

**1. Clone o repositório:**
```bash
git clone https://github.com/phkaiser13/Bybo.git
cd Bybo
```

**2. Execute a aplicação (Modo de Desenvolvimento):**

<p>Este comando compila e inicia a aplicação em modo de desenvolvimento.</p>

```bash
mvn clean javafx:run
```


3. Gerando o Pacote Final:

<p>Para gerar o arquivo <code>.jar</code> executável da aplicação.</p>

```bash

#Empacota a aplicação
mvn clean package

Executa o arquivo .jar gerado

java -jar bybo-app/target/bybo-app-1.0.0-SNAPSHOT.jar
```

📄 Licença

Este projeto é distribuído sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

<hr>

<p align="center">
Feito com ❤️ por Pedro Henrique Garcia
<br>
<a href="https://github.com/phkaiser13"><strong>GitHub: @phkaiser13</strong></a>
</p>

