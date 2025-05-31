# 📖 Manual do Usuário

## 🧪 Requisitos para rodar o sistema

- Java 17
- Maven instalado
- PostgreSQL rodando e configurado
- Arquivo `.env` (ou similar)
- URL de conexão definida em `ConexaoBD.java` com suas credenciais

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
🔘 **"Fazer Inscrição"**

### 📣 O que acontece:
- Participante é salvo no banco.
- O sistema cria a relação com todos os eventos selecionados.
- Exibe mensagem:
  > `Cadastrado com sucesso! ID: X`

- Também é possível acessar o botão **Minhas Inscrições**:
  - Informa ID e e-mail para visualizar eventos em que está inscrito
  - Pode editar nome e e-mail
  - Pode emitir certificado com download em PDF
  - Pode excluir inscrição de um evento

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
- Campos obrigatórios devem estar preenchidos.
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
  > `Evento cadastrado com sucesso!`
  >> `Palestrante cadastrado com ID: X` (Importante guardar esse identificador)

---

## 👁️ Tela: **Listar Eventos**

Aba usada para **visualizar, editar ou excluir eventos existentes**, além de ver os participantes inscritos.

### 📝 Ações disponíveis:
- Atualizar Evento (com autenticação usando o identificador e email do palestrante)
- Excluir Evento (com autenticação usando o identificador e email do palestrante)
- Ver Participantes:
  - Lista todos os participantes com nome e e-mail **parcialmente censurado** por segurança
  - Exibe mensagem:
    > `Nenhum participante ainda` caso não haja inscritos

---

## 📦 Execução

### Compilar e executar:
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.sistemaevento.MenuPrincipalSwing"
```
