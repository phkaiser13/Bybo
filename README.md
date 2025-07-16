<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ByBo - Sistema de Gerenciamento de Biblioteca</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');

        :root {
            --bg-color: #121212;
            --primary-color: #1e1e1e;
            --secondary-color: #2a2a2a;
            --font-color: #e0e0e0;
            --highlight-color: #bb86fc;
            --border-radius: 12px;
            --transition-speed: 0.3s;
        }

        body {
            font-family: 'Roboto', sans-serif;
            background-color: var(--bg-color);
            color: var(--font-color);
            line-height: 1.6;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .container {
            width: 100%;
            max-width: 900px;
            padding: 20px;
            box-sizing: border-box;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        section {
            background-color: var(--primary-color);
            margin: 20px 0;
            padding: 25px;
            border-radius: var(--border-radius);
            animation: fadeIn 0.8s ease-in-out;
            border: 1px solid #333;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        }

        .header {
            text-align: center;
            padding: 40px 0;
        }

        .header h1 {
            font-size: 3em;
            color: var(--highlight-color);
            margin: 0;
            letter-spacing: 2px;
        }

        .header p {
            font-size: 1.2em;
            color: #b0b0b0;
        }

        .header .main-svg {
            width: 200px;
            height: 200px;
            margin: 20px auto;
        }

        h2 {
            display: flex;
            align-items: center;
            font-size: 2em;
            color: var(--highlight-color);
            border-bottom: 2px solid var(--highlight-color);
            padding-bottom: 10px;
            margin-bottom: 20px;
        }

        h2 img {
            margin-right: 15px;
        }

        ul {
            list-style: none;
            padding: 0;
        }

        li {
            background-color: var(--secondary-color);
            padding: 15px;
            margin-bottom: 10px;
            border-radius: 8px;
            display: flex;
            align-items: center;
            transition: transform var(--transition-speed), background-color var(--transition-speed);
        }
        
        li:hover {
            transform: translateY(-3px);
            background-color: #3c3c3c;
        }

        li strong {
            color: #dcdcdc;
        }

        .download-section {
            text-align: center;
            padding: 30px;
        }
        
        .download-button {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 15px 30px;
            background: linear-gradient(45deg, #8e44ad, #bb86fc);
            color: white;
            text-decoration: none;
            font-size: 1.2em;
            font-weight: bold;
            border-radius: 50px;
            transition: transform var(--transition-speed), box-shadow var(--transition-speed);
            border: none;
        }

        .download-button img {
            margin-right: 10px;
        }
        
        .download-button:hover {
            transform: scale(1.05);
            box-shadow: 0 5px 20px rgba(187, 134, 252, 0.4);
        }

        pre {
            background-color: #0d0d0d;
            padding: 20px;
            border-radius: var(--border-radius);
            overflow-x: auto;
            border: 1px solid #333;
        }

        code {
            font-family: 'Courier New', Courier, monospace;
            color: #a5d6a7; /* Light green for code */
        }
        
        code.comment {
            color: #666;
        }

        .footer {
            text-align: center;
            padding: 30px 0;
            color: #777;
            font-size: 0.9em;
        }
    </style>
</head>
<body>

    <div class="container">

        <header class="header">
            <svg class="main-svg" viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg">
                <defs>
                    <linearGradient id="grad1" x1="0%" y1="0%" x2="100%" y2="100%">
                        <stop offset="0%" style="stop-color:#bb86fc;stop-opacity:1" />
                        <stop offset="100%" style="stop-color:#8e44ad;stop-opacity:1" />
                    </linearGradient>
                </defs>
                <path fill="url(#grad1)" d="M50,5 C25.1,5 5,25.1 5,50 C5,74.9 25.1,95 50,95 C74.9,95 95,74.9 95,50 C95,25.1 74.9,5 50,5 Z M50,85 C30.7,85 15,69.3 15,50 C15,30.7 30.7,15 50,15 C69.3,15 85,30.7 85,50 C85,69.3 69.3,85 50,85 Z" />
                <path fill="#e0e0e0" d="M40,30 L60,30 L60,70 L40,70 Z">
                    <animateTransform attributeName="transform" type="rotate" from="0 50 50" to="360 50 50" dur="10s" repeatCount="indefinite" />
                </path>
                <path fill="#bb86fc" d="M35,25 L65,25 L65,75 L35,75 Z">
                     <animateTransform attributeName="transform" type="rotate" from="360 50 50" to="0 50 50" dur="15s" repeatCount="indefinite" />
                </path>
            </svg>
            <h1>📚 ByBo</h1>
            <p>Um sistema de gerenciamento de biblioteca moderno, robusto e interativo.</p>
        </header>
        
        <section class="download-section">
            <h2>
                <img src="https://img.icons8.com/fluency/48/windows10.png" alt="Windows Icon" width="48" height="48"/>
                Instalador para Windows
            </h2>
            <p>Não quer compilar o código? Baixe o instalador `.msi` pronto para uso.</p>
            <a href="https://github.com/phkaiser13/Bybo/releases/download/1.0-Def/ByBo-1.0.0.msi" class="download-button" target="_blank">
                <img src="https://img.icons8.com/fluency/48/download.png" alt="Download icon" width="32" height="32"/>
                Download ByBo-1.0.0.msi
            </a>
        </section>

        <section id="features">
            <h2>
                <img src="https://img.icons8.com/fluency/48/confetti.png" alt="Features Icon" width="48" height="48"/>
                Funcionalidades
            </h2>
            <ul>
                <li><strong>Interface Gráfica Moderna:</strong> UI escura e elegante construída com JavaFX e estilizada com CSS.</li>
                <li><strong>Navegação Fluida:</strong> Barra de navegação lateral com transições animadas suaves entre as telas.</li>
                <li><strong>Gerenciamento Completo (CRUD):</strong> Adicione, edite e exclua livros de forma intuitiva.</li>
                <li><strong>Busca Dinâmica:</strong> Filtre a lista de livros em tempo real enquanto você digita.</li>
                <li><strong>Lógica de Empréstimo/Devolução:</strong> Altere o status dos livros com um clique, com feedback visual imediato.</li>
                <li><strong>Dashboard com Estatísticas:</strong> Uma visão geral da biblioteca com o total de livros, disponíveis e emprestados.</li>
                <li><strong>Persistência de Dados Eficiente:</strong> Os dados são salvos com **MessagePack**, mais rápido e compacto que JSON.</li>
                <li><strong>Validação Visual:</strong> Campos de formulário inválidos são destacados para guiar o usuário.</li>
            </ul>
        </section>

        <section id="tech">
            <h2>
                <img src="https://img.icons8.com/fluency/48/maintenance.png" alt="Tools Icon" width="48" height="48"/>
                Ferramentas e Tecnologias
            </h2>
            <ul>
                <li><strong>Linguagens:</strong> <strong>Java 17</strong> & <strong>Kotlin</strong> (demonstrando interoperabilidade).</li>
                <li><strong>Framework UI:</strong> <strong>JavaFX 17</strong> para a construção de toda a interface gráfica.</li>
                <li><strong>Build & Dependências:</strong> <strong>Apache Maven</strong> para gerenciar o projeto multi-módulo.</li>
                <li><strong>Persistência:</strong> <strong>MessagePack</strong> com a biblioteca Jackson para serialização eficiente.</li>
                <li><strong>Ícones:</strong> <strong>Ikonli</strong> para ícones vetoriais nítidos e facilmente estilizáveis.</li>
            </ul>
        </section>

        <section id="architecture">
            <h2>
                <img src="https://img.icons8.com/fluency/48/flow-chart.png" alt="Architecture Icon" width="48" height="48"/>
                Arquitetura Modular
            </h2>
            <p>O projeto segue as melhores práticas de design, dividido em três módulos independentes para garantir baixo acoplamento e alta coesão:</p>
            <ul>
                <li><strong><code>bybo-core</code>:</strong> O coração do sistema. Contém as entidades de negócio e interfaces dos repositórios.</li>
                <li><strong><code>bybo-persistence</code>:</strong> A camada de dados. Implementa a leitura e escrita dos dados no disco.</li>
                <li><strong><code>bybo-app</code>:</strong> A camada de apresentação. Contém toda a lógica da interface gráfica (views, controllers, CSS).</li>
            </ul>
        </section>

        <section id="run">
            <h2>
                <img src="https://img.icons8.com/fluency/48/rocket.png" alt="Rocket Icon" width="48" height="48"/>
                Como Executar
            </h2>
            <p>Você precisará do <strong>JDK 17</strong> (ou superior) e do <strong>Apache Maven</strong> instalados.</p>
            
            <h3>1. Clone o repositório:</h3>
            <pre><code>git clone https://github.com/phkaiser13/Bybo.git
cd Bybo</code></pre>

            <h3>2. Execute a aplicação:</h3>
            <p><strong>Modo de Desenvolvimento (recomendado):</strong></p>
            <pre><code><code class="comment"># Compila e inicia a aplicação rapidamente.</code>
mvn clean javafx:run</code></pre>
            
            <p><strong>Gerando o Pacote Final:</strong></p>
            <pre><code><code class="comment"># 1. Empacota a aplicação em um .jar "tudo-em-um"</code>
mvn clean package

<code class="comment"># 2. Executa o arquivo .jar gerado</code>
java -jar bybo-app/target/bybo-app-1.0.0-SNAPSHOT.jar</code></pre>
        </section>

        <section id="license">
            <h2>
                <img src="https://img.icons8.com/fluency/48/certificate.png" alt="License Icon" width="48" height="48"/>
                Licença
            </h2>
            <p>Distribuído sob a licença MIT. Veja o arquivo <code>LICENSE</code> para mais informações.</p>
        </section>

        <footer class="footer">
            <p>// 2025 // By Pedro Henrique Garcia //</p>
            <p>
                <a href="https://github.com/phkaiser13" style="color: var(--highlight-color); text-decoration: none;">GitHub: phkaiser13</a>
            </p>
        </footer>

    </div>

</body>
</html>
