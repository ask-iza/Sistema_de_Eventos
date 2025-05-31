# 📘 Documentação Técnica – SistemaEvento

---

## 📌 1. Informações Gerais

- **Projeto:** SistemaEvento  
- **Tecnologia principal:** Java 17 (Maven)  
- **Banco de Dados:** PostgreSQL  
- **Interface gráfica:** Java Swing  
- **Gerenciador de dependências:** Maven  

---

## 🎯 2. Objetivo do Sistema

O SistemaEvento é uma aplicação desktop que permite:

- Cadastrar **participantes** e vinculá-los a eventos
- Cadastrar **palestrantes**, evitando duplicatas, e associá-los automaticamente a novos eventos
- Gerenciar eventos com controle de inscrições e vínculos entre entidades
- Visualizar participantes e ações vinculadas a cada evento

---

## 🏗️ 3. Arquitetura e Estrutura do Projeto

A estrutura do projeto é organizada por responsabilidades:

| Pacote        | Responsabilidade                                |
|---------------|--------------------------------------------------|
| `dao`         | Camada de acesso ao banco de dados (JDBC)       |
| `service`     | Lógica de negócio (intermediação entre front e banco) |
| `tabelas`     | Entidades que refletem a estrutura do banco     |
| `front`       | Interface do usuário via Java Swing             |
| `util`        | Configuração de conexão e utilitários           |

---

## 🔄 4. Fluxo de Funcionamento

### ▶️ Classe Inicial: `MenuPrincipalSwing`

Essa classe é o ponto de entrada do sistema e permite a navegação entre abas.

---

### 👤 Participante

1. Usuário preenche: **nome**, **email**, **eventos**
2. É feita a validação do e-mail
3. Participante é salvo no banco
4. Uma entrada é criada na tabela `inscricoes` para cada evento selecionado

---

### 🎤 Palestrante + Evento

1. Usuário preenche dados do palestrante e evento
2. Verificação se o palestrante já existe (nome e e-mail)
   - Se sim, não cadastra duplicado
   - Se não, cadastra novo
3. Evento é criado
4. Palestrante é vinculado ao evento via `evento_palestrante`

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

## ⚙️ 6. Configuração do Ambiente

### 📦 Dependência Maven:

```xml
<dependencies>
  <dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.5.0</version>
  </dependency>
</dependencies>
```

### 🔌 Conexão com o banco (ConexaoBD.java):

```java
conn = DriverManager.getConnection(
  "jdbc:postgresql://localhost:5432/seubanco",
  "usuario", "senha"
);
```

> **Atenção:** o `.env` ou credenciais sensíveis **não devem ser versionados no GitHub**.

---

## 🧪 7. Execução

### Compilar:
```bash
mvn compile
```

### Executar:
```bash
mvn exec:java -Dexec.mainClass="com.sistemaevento.MenuPrincipalSwing"
```

---

## 👥 8. Equipe e Autoria

| Membro     | Responsável por:                             |
|------------|----------------------------------------------|
| **Victor**   | Interface Swing e usabilidade              |
| **Samantha** | Lógica de negócio e entidades (services)   |
| **Rayssa**   | Integração com PostgreSQL e camada DAO     |

---

> 🛠️ Atualizado em: **Maio**