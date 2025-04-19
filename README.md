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

O SistemaEvento é uma aplicação de desktop desenvolvida em Java com o objetivo de:

- Cadastrar **participantes** e vinculá-los a eventos
- Cadastrar **palestrantes** e associá-los automaticamente aos eventos
- Manter o histórico de inscrições e relações entre entidades no banco

---

## 🏗️ 3. Arquitetura e Estrutura

O projeto segue uma estrutura dividida por pacotes:

| Pacote | Função |
|--------|--------|
| `dao` | Acesso ao banco de dados (JDBC) |
| `service` | Regras de negócio e lógica intermediária |
| `tabelas` | Entidades que mapeiam as tabelas do banco |
| `front` | Telas Swing (interface com o usuário) |
  | `MenuPrinciaplSwing` | Classe Princiapl de execução |
| `util` | Configuração de conexão com o banco |

---

## 🔄 4. Fluxo de Funcionamento

### Participante:
1. Usuário preenche nome, email e seleciona eventos
2. Participante é salvo e seu ID é recuperado
3. Para cada evento selecionado, é criada uma inscrição na tabela `inscricoes`

### Palestrante:
1. Usuário insere dados do palestrante e do evento
2. Ambos são salvos separadamente
3. É criado o vínculo entre evento e palestrante na tabela `evento_palestrante`

---

## 🧩 5. Estrutura do Banco de Dados

### Tabelas:

- `participante(id, nome, email)`
- `palestrante(id, nome, curriculo, area_atuacao)`
- `evento(id, nome, descricao, data, local, capacidade)`
- `evento_palestrante(evento_id, palestrante_id)`
- `inscricoes(participante_id, evento_id)`

### Relacionamentos:

- `participante` ↔ `evento`: N:N (via `inscricoes`)
- `palestrante` ↔ `evento`: N:N (via `evento_palestrante`)

---

## ⚙️ 6. Configuração do Ambiente

### Dependências Maven:

```xml
<dependencies>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.5.0</version>
    </dependency>
</dependencies>
```

### Conexão ao banco (`ConexaoBD.java`):

```java
conn = DriverManager.getConnection(
  "jdbc:postgresql://localhost:5432/seubanco",
  "usuario", "senha"
);
```

---

## 🧪 7. Execução

### Compilar:
```bash
mvn compile
```

### Executar:
```bash
mvn exec:java -Dexec.mainClass="com.sistemaevento.Run"
```

---

## 👥 8. Equipe (Commits e Autores)

| Autor     | Responsável |
|-----------|-------------|
| **Victor**   | Interface gráfica (Swing) |
| **Samantha** | Acesso ao banco (DAO, conexão, execução) |
| **Rayssa**   | Lógica de negócio (services e entidades) |
