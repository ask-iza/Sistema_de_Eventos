# 📖 Manual do Usuário

## 🧪 Requisitos para rodar o sistema

- Java 17
- Maven instalado
- PostgreSQL rodando e configurado
- Arquivo `.env` (ou similar)
- URL de conexão definida em `ConexaoBD.java` com suas credenciais

---

---

### 🚀 Classe principal: `MenuPrincipalSwing`

Essa é a classe inicial que abre a interface gráfica do sistema.

---

## 🟩 Tela: **Participante**

Aba usada para **cadastrar participantes** e **inscrevê-los em eventos existentes** com exibição detalhada de cada evento.

### ✅ O que preencher:
- **Nome**
- **Email** (obrigatório conter `@` e `.com`)
- Selecionar pelo menos um evento (com nome, data, local, capacidade e nome do palestrante visíveis)

### 🟢 Clique em:
🔘 **"Cadastrar e Inscrever"**

### 📣 O que acontece:
- Participante é salvo no banco.
- O sistema cria a relação com todos os eventos selecionados.
- Exibe mensagem:
  > `Cadastrado com sucesso! ID: X`

---

## 🟨 Tela: **Palestrante + Evento**

Aba usada para **registrar um novo palestrante** e **criar um evento associado a ele**.

### ✅ O que preencher:

#### 📋 Palestrante:
- Nome do palestrante
- Currículo
- Área de atuação
- Email

#### 🗓️ Evento:
- Nome do evento
- **Descrição** (opcional)
- Data (formato: `dd/mm/aaaa`)
- Local
- Capacidade (somente números)

### ⚠️ Validações:
- Campos obrigatórios devem estar preenchidos (exceto descrição).
- Email válido deve conter `@` e `.com`.

### 🔄 Comportamento inteligente:
- Se o palestrante **já existir** (mesmo nome e email), **não é duplicado no banco** — apenas vinculado ao novo evento.

### 🟢 Clique em:
🔘 **"Cadastrar"**

### 📣 O que acontece:
- O sistema verifica se o palestrante já existe.
- Cadastra o evento.
- Cria o vínculo com o palestrante.
- Exibe mensagem:
  > `Evento cadastrados com sucesso!`
  > `Palestrante cadastrado com ID: X` (Importante guardar esse identificador)

---

## 👁️ Tela: **Listar Eventos**

Aba usada para **visualizar, editar ou excluir eventos existentes**, além de ver os participantes inscritos.

### 📝 Ações disponíveis:
- Atualizar Evento (com autenticação usando o identificador e email do palestrante)
- Excluir Evento (com autenticação usando o identificador e email do palestrante)
- Ver Participantes

### 📣 Quando não há participantes:
Exibe a mensagem centralizada:
> `Nenhum participante ainda`

---

## 📦 Execução

### Compilar:
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.sistemaevento.MenuPrincipalSwing"
---
