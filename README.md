<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ByBo README</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap');

        body {
            font-family: 'Inter', sans-serif;
            background-color: #0d1117;
            color: #c9d1d9;
            line-height: 1.6;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 890px;
            margin: 40px auto;
            padding: 20px;
            background-color: #161b22;
            border: 1px solid #30363d;
            border-radius: 10px;
        }

        .header {
            text-align: center;
            border-bottom: 1px solid #30363d;
            padding-bottom: 20px;
            margin-bottom: 30px;
        }

        .header h1 {
            font-size: 2.8em;
            font-weight: 700;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 15px;
            color: #58a6ff;
            margin: 0;
        }

        .header p {
            font-size: 1.1em;
            color: #8b949e;
            max-width: 650px;
            margin: 10px auto 0;
        }

        .preview-image {
            width: 100%;
            max-width: 750px;
            border-radius: 8px;
            margin-top: 30px;
            border: 1px solid #30363d;
        }
        
        .download-button {
            display: inline-block;
            margin-top: 25px;
            background-color: #238636;
            color: #ffffff;
            padding: 12px 25px;
            border-radius: 6px;
            text-decoration: none;
            font-weight: 600;
            transition: background-color 0.3s ease;
            font-size: 1.1em;
        }

        .download-button:hover {
            background-color: #2ea043;
        }

        .download-button img {
            vertical-align: middle;
            margin-right: 8px;
            filter: brightness(0) invert(1);
        }

        .section {
            margin-bottom: 40px;
            padding: 20px;
            background-color: #0d1117;
            border: 1px solid #30363d;
            border-radius: 8px;
        }
        
        .section-header {
            display: flex;
            align-items: center;
            gap: 12px;
            border-bottom: 1px solid #30363d;
            padding-bottom: 10px;
            margin-bottom: 20px;
        }

        .section-header h2 {
            font-size: 1.8em;
            color: #58a6ff;
            margin: 0;
        }

        .section ul {
            list-style: none;
            padding-left: 0;
        }

        .section ul li {
            background-color: #161b22;
            padding: 12px 15px;
            border-radius: 6px;
            margin-bottom: 8px;
            border-left: 3px solid #58a6ff;
            color: #c9d1d9;
        }

        .tech-stack, .architecture-modules {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            justify-content: center;
        }

        .tech-item, .module-item {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 8px;
            background-color: #161b22;
            padding: 15px;
            border-radius: 8px;
            min-width: 120px;
            text-align: center;
            border: 1px solid #30363d;
        }

        .tech-item span, .module-item span {
            font-weight: 600;
            color: #8b949e;
        }
        
        .module-item p {
            font-size: 0.85em;
            color: #8b949e;
            margin: 0;
            max-width: 200px;
        }

        code-container {
            background-color: #010409;
            border: 1px solid #30363d;
            border-radius: 6px;
            padding: 15px;
            margin-top: 15px;
            overflow-x: auto;
        }

        code {
            font-family: 'SFMono-Regular', Consolas, 'Liberation Mono', Menlo, monospace;
            color: #c9d1d9;
            font-size: 0.9em;
        }
        
        .footer {
            text-align: center;
            margin-top: 40px;
            padding-top: 20px;
            border-top: 1px solid #30363d;
            color: #8b949e;
        }
    </style>
</head>
<body>

    <div class="container">

        <div class="header">
            <img src="https://raw.githubusercontent.com/phkaiser13/Bybo/main/.github/resources/bybo-logo-animated.svg" alt="ByBo Animated Logo" width="400">
            <p>Um sistema de gerenciamento de biblioteca moderno, robusto e modular. Projetado para ser uma ferramenta completa e um excelente exemplo de engenharia de software com Java.</p>
            <img class="preview-image" src="https://raw.githubusercontent.com/phkaiser13/Bybo/main/.github/resources/Preview.png" alt="Screenshot do ByBo">
            
            <a href="https://github.com/phkaiser13/Bybo/releases/download/1.0-Def/ByBo-1.0.0.msi" class="download-button">
                <img src="https://img.icons8.com/material-outlined/24/000000/installing-updates.png" alt="Download Icon" width="24" height="24">
                Download para Windows (.msi)
            </a>
        </div>

        <div class="section">
            <div class="section-header">
                <img src="https://img.icons8.com/fluency/48/000000/confetti.png" alt="Features Icon" width="40" height="40"/>
                <h2>✨ Funcionalidades</h2>
            </div>
            <ul>
                <li><strong>Interface Gráfica Moderna:</strong> UI escura e elegante construída com JavaFX e estilizada com CSS.</li>
                <li><strong>Navegação Fluida:</strong> Barra de navegação lateral com transições animadas suaves entre as telas.</li>
                <li><strong>Gerenciamento Completo (CRUD):</strong> Adicione, edite e exclua livros de forma intuitiva.</li>
                <li><strong>Busca Dinâmica:</strong> Filtre a lista de livros em tempo real enquanto você digita.</li>
                <li><strong>Lógica de Empréstimo/Devolução:</strong> Altere o status dos livros com um clique e feedback visual imediato.</li>
                <li><strong>Dashboard com Estatísticas:</strong> Visão geral da biblioteca com o total de livros, disponíveis e emprestados.</li>
                <li><strong>Persistência de Dados Eficiente:</strong> Dados salvos entre sessões usando o formato binário <strong>MessagePack</strong>.</li>
                <li><strong>Validação Visual:</strong> Campos de formulário inválidos são destacados para guiar o usuário.</li>
            </ul>
        </div>

        <div class="section">
            <div class="section-header">
                 <img src="https://img.icons8.com/fluency/48/000000/maintenance.png" alt="Tools Icon" width="40" height="40"/>
                <h2>🛠️ Ferramentas e Tecnologias</h2>
            </div>
            <div class="tech-stack">
                <div class="tech-item"><img src="https://img.icons8.com/color/48/000000/java-coffee-cup-logo.png" alt="Java"><span>Java 17</span></div>
                <div class="tech-item"><img src="https://img.icons8.com/color/48/000000/kotlin.png" alt="Kotlin"><span>Kotlin</span></div>
                <div class="tech-item"><img src="https://img.icons8.com/fluency/48/000000/node-js.png" alt="JavaFX"><span>JavaFX 17</span></div>
                <div class="tech-item"><img src="https://img.icons8.com/color/48/000000/maven.png" alt="Maven"><span>Maven</span></div>
                <div class="tech-item"><img src="https://img.icons8.com/fluency/48/000000/data-transfer.png" alt="MessagePack"><span>MessagePack</span></div>
                <div class="tech-item"><img src="https://img.icons8.com/fluency/48/000000/paint-palette.png" alt="Ikonli"><span>Ikonli</span></div>
            </div>
        </div>

        <div class="section">
            <div class="section-header">
                <img src="https://img.icons8.com/fluency/48/000000/network.png" alt="Architecture Icon" width="40" height="40"/>
                <h2>🏗️ Arquitetura Modular</h2>
            </div>
            <div style="text-align: center; margin-bottom: 25px;">
                <img src="https://raw.githubusercontent.com/phkaiser13/Bybo/main/.github/resources/arch-animated.svg" alt="Arquitetura Animada" width="550">
            </div>
            <div class="architecture-modules">
                <div class="module-item">
                    <img src="https://img.icons8.com/fluency/48/FFFFFF/like.png" alt="Core Module Icon" width="48"/>
                    <h3>bybo-core</h3>
                    <p>O coração do sistema. Contém as entidades de negócio e interfaces. Sem dependências de UI ou persistência.</p>
                </div>
                <div class="module-item">
                    <img src="https://img.icons8.com/fluency/48/FFFFFF/database.png" alt="Persistence Module Icon" width="48"/>
                    <h3>bybo-persistence</h3>
                    <p>A camada de dados. Implementa a lógica para ler e escrever os dados dos livros no disco.</p>
                </div>
                <div class="module-item">
                    <img src="https://img.icons8.com/fluency/48/FFFFFF/imac.png" alt="App Module Icon" width="48"/>
                    <h3>bybo-app</h3>
                    <p>A camada de apresentação (UI). Contém as views, controllers e a lógica da interface gráfica.</p>
                </div>
            </div>
        </div>

        <div class="section">
            <div class="section-header">
                <img src="https://img.icons8.com/fluency/48/000000/rocket.png" alt="Rocket Icon" width="40" height="40"/>
                <h2>🚀 Como Executar</h2>
            </div>
            <p>Você precisará do <strong>JDK 17</strong> (ou superior) e do <strong>Apache Maven</strong> instalados.</p>
            <p><strong>1. Clone o repositório:</strong></p>
            <code-container><code>git clone https://github.com/phkaiser13/Bybo.git && cd Bybo</code></code-container>
            
            <p><strong>2. Execute a aplicação (Modo de Desenvolvimento):</strong></p>
            <code-container><code>mvn clean javafx:run</code></code-container>

            <p><strong>3. Ou gere o pacote final para distribuição:</strong></p>
            <code-container><code># 1. Empacota a aplicação em um .jar
mvn clean package

# 2. Executa o arquivo .jar gerado
java -jar bybo-app/target/bybo-app-1.0.0-SNAPSHOT.jar</code></code-container>
        </div>

        <div class="section">
            <div class="section-header">
                <img src="https://img.icons8.com/fluency/48/000000/test-account.png" alt="License Icon" width="40" height="40"/>
                <h2>📄 Licença</h2>
            </div>
            <p>Distribuído sob a licença MIT. Veja o arquivo <code>LICENSE</code> para mais informações.</p>
        </div>

        <div class="footer">
            <p>// 2025 // By Pedro Henrique Garcia //</p>
            <a href="https://github.com/phkaiser13" style="text-decoration: none; color: #58a6ff;">
                <img src="https://img.icons8.com/fluent/24/000000/github.png" alt="GitHub" style="vertical-align: middle;"/>
                phkaiser13
            </a>
        </div>

    </div>

</body>
</html>
