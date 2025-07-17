
📚 ByBo - Sistema de Gerenciamento de Biblioteca
Um projeto de demonstração de engenharia de software que une uma arquitetura robusta a uma GUI moderna e interativa com JavaFX.

Tecnologias utilizadas:
- Java 17
- Kotlin 1.9
- JavaFX 17
- Maven 4.0
- Licença MIT

---

🚀 Para Usuários: Baixe e Instale

Clique no link abaixo para baixar o instalador para Windows:
https://github.com/phkaiser13/Bybo/releases/download/1.0-Def/ByBo-1.0.0.msi

---

✨ Funcionalidades Principais

- 🎨 Interface Gráfica Moderna: UI escura e elegante construída com JavaFX e estilizada com CSS.
- ➡️ Navegação Fluida: Transições animadas e suaves entre as telas para uma melhor experiência de usuário.
- 💾 Gerenciamento Completo (CRUD): Adicione, edite e exclua livros de forma intuitiva.
- 🔍 Busca Dinâmica: Filtre e encontre livros na sua coleção em tempo real.
- 🔄 Lógica de Empréstimo/Devolução: Altere o status dos livros (disponível/emprestado) com um único clique.
- 📊 Dashboard com Estatísticas: Tenha uma visão geral da sua biblioteca com dados relevantes.
- 📦 Persistência de Dados Eficiente: Os dados da biblioteca são salvos localmente usando o formato MessagePack.
- ⚠️ Validação Visual: Campos de formulário inválidos são destacados para facilitar a correção.

---

🛠️ Ferramentas e Tecnologias

- Java 17
- Kotlin 1.9
- JavaFX 17
- Maven 4.0
- MessagePack (Serialização)
- CSS3 (Estilização)

---

🏗️ Arquitetura Modular

O projeto é dividido em três módulos, seguindo as melhores práticas de design de software para garantir baixo acoplamento e alta coesão:

- bybo-core: O coração do sistema. Contém as entidades de negócio e as interfaces dos repositórios.
- bybo-persistence: A camada de dados. Implementa o repositório para ler e escrever os dados da aplicação.
- bybo-app: A camada de apresentação. Contém a lógica da interface gráfica e a interação com o usuário.

---

👨‍💻 Para Desenvolvedores: Executando o Projeto

Requisitos: JDK 17 (ou superior) e Apache Maven

1. Clone o repositório:
git clone https://github.com/phkaiser13/Bybo.git
cd Bybo

2. Execute a aplicação (Modo de Desenvolvimento):
mvn clean javafx:run

3. Gerando o Pacote Final:
# Empacota a aplicação
mvn clean package

# Executa o arquivo .jar gerado
java -jar bybo-app/target/bybo-app-1.0.0-SNAPSHOT.jar

---

📄 Licença

Este projeto é distribuído sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

---

Feito com ❤️ por Pedro Henrique Garcia
GitHub: @phkaiser13
https://github.com/phkaiser13
