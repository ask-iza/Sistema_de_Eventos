# Sistema de Gerenciamento de Eventos

## Visão Geral
O Sistema de Gerenciamento de Eventos é uma aplicação desenvolvida em Java para facilitar o gerenciamento de eventos, palestrantes e participantes. O sistema segue boas práticas de engenharia de software e inclui funcionalidades completas de cadastro, edição, exclusão e busca, além de oferecer uma interface gráfica intuitiva criada com Swing.

## Estrutura do Projeto
O projeto adota uma arquitetura modular, dividida em três camadas principais:

- **Camada de Aplicação:** Interface gráfica do usuário (Swing Forms) para interação com o sistema.
- **Camada de Serviços:** Regras de negócio intermediando a interface do usuário e a camada de dados.
- **Camada de Dados:** DAOs (Data Access Objects) para operações CRUD com o banco de dados SQLite.

## Funcionalidades Principais
1. **Gerenciamento de Eventos**
   - Cadastro de Eventos: Criação de novos eventos com nome, descrição, data, local e capacidade de participantes.
   - Edição de Eventos: Atualização de informações de eventos cadastrados.
   - Exclusão de Eventos: Remoção de eventos do sistema.
   - Consulta de Eventos: Pesquisa utilizando filtros como nome ou data.
2. **Gerenciamento de Palestrantes**
   - Cadastro de Palestrantes: Registro de palestrantes com nome, área de especialização e biografia.
   - Associação a Eventos: Vinculação de palestrantes a eventos específicos.
   - Edição e Exclusão: Atualização ou remoção de palestrantes do sistema.
3. **Gerenciamento de Participantes**
   - Cadastro de Participantes: Registro de participantes com informações pessoais e preferências.
   - Inscrição em Eventos: Permissão para inscrição de participantes em eventos.
   - Consulta de Participantes: Pesquisa detalhada de participantes cadastrados.
4. **Funcionalidades Extras**
   - Menu Principal: Navegação simples entre as telas do sistema.
   - Validação de Dados: Garantia de preenchimento correto com campos obrigatórios e formatos validados.
   - Mensagens de Confirmação: Pop-ups para melhorar a experiência do usuário.

## Tecnologias Utilizadas
- **Linguagem de Programação:** Java
- **Banco de Dados:** SQLite
- **Interface Gráfica:** Java Swing
- **Ferramenta de Build:** Apache Maven
- **Controle de Versão:** Git/GitHub

## Configuração e Execução

### Requisitos
- JDK 8 ou superior
- Apache Maven instalado
- SQLite instalado

### Configuração do Ambiente
1. Clone o repositório do projeto:
   ```bash
   git clone <URL_DO_REPOSITORIO>

2. Navegue até o diretório do projeto:3
   ```bash
   cd SistemaEvento
   
3. Construa o projeto com Maven:
    ```bash
    mvn clean install
    
### Execução
Execute o arquivo JAR gerado:
```bash
java -jar target/SistemaEvento-1.0.jar
```
**Acesse o sistema através da interface Swing exibida.**

## Estrutura do Banco de Dados
O banco de dados SQLite possui as seguintes tabelas principais:

- Evento: Armazena dados de eventos.
- Palestrante: Armazena informações de palestrantes.
- Participante: Armazena dados de participantes.
- Palestrante_Evento: Relaciona palestrantes com eventos.
- Participante_Evento: Relaciona participantes com eventos.

### Modelo Relacional
```bash
Evento (1:N) Palestrante_Evento (N:1) Palestrante
Evento (1:N) Participante_Evento (N:1) Participante



