
# 📘 Documentação Técnica

---

## 📌 1. Informações Gerais

- **Projeto:** SistemaEvento  
- **Tecnologia principal:** Java 17 (Maven)  
- **Banco de Dados:** PostgreSQL  
- **Interface gráfica:** Java Swing  
- **Gerenciador de dependências:** Maven  
- **Geração de PDF:** iText (certificados)  

---

## 🎯 2. Objetivo do Sistema

O SistemaEvento é uma aplicação desktop que permite:

- Cadastrar **participantes** e vinculá-los a eventos
- Cadastrar **palestrantes**, evitando duplicatas, e associá-los automaticamente a novos eventos
- Gerenciar eventos com controle de inscrições e vínculos entre entidades
- Visualizar participantes de cada evento (com e-mails parcialmente ocultos)
- Emitir certificados de participação em PDF (com layout paisagem e dados personalizados)

---

## 🏗️ 3. Arquitetura e Estrutura do Projeto

A estrutura do projeto é organizada por responsabilidades:

| Pacote        | Responsabilidade                                |
|---------------|--------------------------------------------------|
| `dao`         | Camada de acesso ao banco de dados (JDBC)       |
| `service`     | Lógica de negócio (intermediação entre front e banco) |
| `tabelas`     | Entidades que refletem a estrutura do banco     |
| `front`       | Interface do usuário via Java Swing             |
| `util`        | Conexão com banco e utilitários       |

---

## 🔄 4. Fluxo de Funcionamento

### ▶️ Classe Inicial: `MenuPrincipalSwing`

Responsável por iniciar a interface principal com as abas de funcionalidades.

---

### 👤 Participante

- Cadastro de participante com nome, e-mail e seleção de eventos
- E-mail é validado para conter `@` e `.com`
- Participantes existentes são reutilizados para evitar duplicações
- A tela “Minhas Inscrições” permite:
  - Editar nome e e-mail
  - Emitir certificado do evento
  - Cancelar inscrição

---

### 🎤 Palestrante + Evento

- Cadastro integrado em uma única tela
- Verificação automática se o palestrante já existe (por nome e e-mail)
- Evita duplicidade no banco
- Evento criado é automaticamente vinculado ao palestrante

---

### 📄 Certificado PDF

- Gerado com layout paisagem
- Inclui título, nome do participante, nome do evento, data e assinatura
- Salvo automaticamente na área de trabalho do usuário
- Emitido diretamente da interface com um clique no botão “Emitir Certificado”

---

## 🧩 5. Estrutura do Banco de Dados

### 🔧 Tabelas principais:

- `participante(id, nome, email)`
- `palestrante(id, nome, curriculo, area_atuacao, email)`
- `evento(id, nome, descricao, data, local, capacidade)`
- `evento_palestrante(evento_id, palestrante_id)`
- `inscricoes(participante_id, evento_id)`

### 🔗 Relacionamentos:

- `participante` ↔ `evento`: muitos para muitos (`inscricoes`)
- `palestrante` ↔ `evento`: muitos para muitos (`evento_palestrante`)

---

## 🆕 6. Funcionalidades Adicionadas

- 📄 **Certificados em PDF** (modo paisagem, personalizados)
- 🔒 **Censura de e-mail de participantes** na tela de listagem (exibe apenas parte, exemplo: `jo***@gmail.com`)
- 🔁 **Atualização do perfil** permite editar nome **e e-mail** diretamente pela interface
- 🧠 **Validação automática** ao reutilizar palestrantes e participantes existentes

---

## ⚙️ 7. Configuração do Ambiente

### 📦 Dependência Maven:

```xml
<dependencies>
  <dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.5.0</version>
  </dependency>
  <dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.5.13.3</version>
  </dependency>
</dependencies>
```

### 🔌 Conexão com o banco (`ConexaoBD.java`):

```java
conn = DriverManager.getConnection(
  "jdbc:postgresql://localhost:5432/seubanco",
  "usuario", "senha"
);
```

> **Atenção:** o `.env` ou credenciais sensíveis **não devem ser versionados no GitHub**.

---

## 🧪 8. Execução

### Compilar:
```bash
mvn compile
```

### Executar:
```bash
mvn exec:java -Dexec.mainClass="com.sistemaevento.MenuPrincipalSwing"
```

---

## 👥 9. Equipe e Autoria

| Membro     | Responsável por:                             |
|------------|----------------------------------------------|
| **Victor**   | Interface Swing, lógica de interação e PDF |
| **Samantha** | DAO e serviços                             |
| **Rayssa**   | Lógica de negócio, integração com PostgreSQL e infraestrutura |

---

> 🛠️ Atualizado em: **Maio de 2025**  
