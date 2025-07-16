<!DOCTYPE html>

<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ByBo - Sistema de Gerenciamento de Biblioteca</title>
</head>
<body>

<div align="center">
<img src="https://raw.githubusercontent.com/phkaiser13/Bybo/main/.github/resources/Preview.png" alt="ByBo - Sistema de Gerenciamento de Biblioteca" width="700"/>
<h1>📚 ByBo - Sistema de Gerenciamento de Biblioteca</h1>
<p>
<strong>Um projeto de demonstração de engenharia de software que une uma arquitetura robusta a uma GUI moderna e interativa com JavaFX.</strong>
</p>
<p>
<img src="https://img.shields.io/badge/Java-17-blue?logo=java&logoColor=white" alt="Java 17"/>
<img src="https://img.shields.io/badge/Kotlin-1.9-blueviolet?logo=kotlin&logoColor=white" alt="Kotlin"/>
<img src="https://img.shields.io/badge/JavaFX-17-orange?logo=openjfx&logoColor=white" alt="JavaFX 17"/>
<img src="https://img.shields.io/badge/Maven-4.0-red?logo=apache-maven&logoColor=white" alt="Maven"/>
<img src="https://img.shields.io/badge/Licença-MIT-green" alt="Licença MIT"/>
</p>
</div>

<div align="center">
<a href="https://github.com/phkaiser13/Bybo/releases/download/1.0-Def/ByBo-1.0.0.msi">
<img src="https://img.icons8.com/fluency/48/000000/windows10.png" alt="Download para Windows"/>
<br>
<strong>Baixar para Windows</strong>
</a>
</div>

✨ Funcionalidades
<table width="100%">
<tr>
<td width="50%" valign="top">
<img src="https://raw.githubusercontent.com/phkaiser13/Bybo/main/.github/resources/Preview.png" alt="Interface Moderna" width="100%"/>
</td>
<td width="50%" valign="top">
<ul>
<li><img src="https://img.icons8.com/fluency/24/000000/paint-palette.png" alt="UI Icon"/> <strong>Interface Gráfica Moderna:</strong> UI escura e elegante com JavaFX e CSS.</li>
<li><img src="https://img.icons8.com/fluency/24/000000/move-right.png" alt="Navegação Icon"/> <strong>Navegação Fluida:</strong> Transições animadas e suaves entre as telas.</li>
<li><img src="https://img.icons8.com/fluency/24/000000/database.png" alt="CRUD Icon"/> <strong>Gerenciamento Completo (CRUD):</strong> Adicione, edite e exclua livros de forma intuitiva.</li>
<li><img src="https://img.icons8.com/fluency/24/000000/search.png" alt="Busca Icon"/> <strong>Busca Dinâmica:</strong> Filtre livros em tempo real.</li>
<li><img src="https://img.icons8.com/fluency/24/000000/synchronize.png" alt="Empréstimo Icon"/> <strong>Lógica de Empréstimo/Devolução:</strong> Altere o status dos livros com um clique.</li>
<li><img src="https://img.icons8.com/fluency/24/000000/dashboard-layout.png" alt="Dashboard Icon"/> <strong>Dashboard com Estatísticas:</strong> Visão geral da biblioteca.</li>
<li><img src="https://img.icons8.com/fluency/24/000000/data-configuration.png" alt="Persistência Icon"/> <strong>Persistência de Dados Eficiente:</strong> Dados salvos com MessagePack.</li>
<li><img src="https://img.icons8.com/fluency/24/000000/error.png" alt="Validação Icon"/> <strong>Validação Visual:</strong> Campos de formulário inválidos são destacados.</li>
</ul>
</td>
</tr>
</table>

🛠️ Ferramentas e Tecnologias
<div align="center">
<img src="https://img.icons8.com/color/48/000000/java-coffee-cup-logo.png" alt="Java"/>
<img src="https://img.icons8.com/color/48/000000/kotlin.png" alt="Kotlin"/>
<img src="https://img.icons8.com/color/48/000000/javafx.png" alt="JavaFX"/>
<img src="https://img.icons8.com/color/48/000000/maven.png" alt="Maven"/>
<img src="https://img.icons8.com/fluency/48/000000/package.png" alt="MessagePack"/>
<img src="https://img.icons8.com/fluency/48/000000/css3.png" alt="CSS3"/>
</div>

🏗️ Arquitetura Modular

O projeto é dividido em três módulos, seguindo as melhores práticas de design de software para garantir baixo acoplamento e alta coesão:

bybo-core: O coração do sistema. Contém as entidades de negócio e as interfaces dos repositórios.

bybo-persistence: A camada de dados. Implementa o repositório para ler e escrever os dados.

bybo-app: A camada de apresentação. Contém a lógica da interface gráfica e a interação com o usuário.

<div align="center">
<img src="https://www.plantuml.com/plantuml/svg/SoWkIImgAStDuNBCoajB2L9DBb8mG5QgoS992G00" alt="Diagrama de Arquitetura"/>
</div>

🚀 Como Executar

Você precisará do JDK 17 (ou superior) e do Apache Maven instalados.

Clone o repositório:

Generated bash
git clone https://github.com/phkaiser13/Bybo.git
cd Bybo


Execute a aplicação (Modo de Desenvolvimento):

Generated bash
mvn clean javafx:run
IGNORE_WHEN_COPYING_START
content_copy
download
Use code with caution.
Bash
IGNORE_WHEN_COPYING_END

Gerando o Pacote Final:

Generated bash
# 1. Empacota a aplicação
mvn clean package

# 2. Executa o arquivo .jar gerado
java -jar bybo-app/target/bybo-app-1.0.0-SNAPSHOT.jar
IGNORE_WHEN_COPYING_START
content_copy
download
Use code with caution.
Bash
IGNORE_WHEN_COPYING_END
📄 Licença

Distribuído sob a licença MIT. Veja LICENSE para mais informações.

<p align="center">
// 2025<br>
// By Pedro Henrique Garcia.<br>
// Github/gitlab: Phkaiser13
</p>

</body>
</html>
