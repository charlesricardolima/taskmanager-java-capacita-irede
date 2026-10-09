# TaskManager Java - Capacita iRede

Projeto desenvolvido para a trilha de Java do Capacita iRede.

O sistema começou como uma aplicação de console para gerenciamento de tarefas e foi evoluído para uma aplicação visual com JavaFX, persistência em banco SQLite via JDBC, testes automatizados com JUnit e uso de Generics.

---

## Objetivo do Projeto

Criar um gerenciador de tarefas capaz de:

- cadastrar tarefas comuns;
- cadastrar tarefas prioritárias;
- listar tarefas;
- editar tarefas;
- marcar tarefas como concluídas;
- remover tarefas;
- filtrar tarefas por status;
- filtrar tarefas por prioridade;
- persistir os dados em banco SQLite;
- validar regras de negócio;
- testar funcionalidades com JUnit.

---

## Tecnologias Utilizadas

- Java 21
- Maven
- JavaFX
- FXML
- CSS
- SQLite
- JDBC
- JUnit 5
- NetBeans
- Git e GitHub

---

## Funcionalidades

### Tarefas

O sistema permite cadastrar dois tipos de tarefa:

1. Tarefa comum
2. Tarefa prioritária

A tarefa prioritária possui uma prioridade definida por ComboBox:

- Baixa
- Media
- Alta

---

### CRUD

O sistema possui as operações principais de CRUD:

| Operação | Situação |
|---|---|
| Criar tarefa | Implementado |
| Listar tarefas | Implementado |
| Editar tarefa | Implementado |
| Concluir tarefa | Implementado |
| Remover tarefa | Implementado |

---

### Filtros

A tela JavaFX possui filtros por:

| Filtro | Opções |
|---|---|
| Status | Todos, Pendentes, Concluidas |
| Prioridade | Todas, Baixa, Media, Alta, Sem prioridade |

---

## Estrutura do Projeto

```text
src/
├── main/
│   ├── java/
│   │   └── br/com/charles/taskmanager/
│   │       ├── app/
│   │       │   ├── App.java
│   │       │   └── MainApplication.java
│   │       ├── controller/
│   │       │   └── TarefaController.java
│   │       ├── dao/
│   │       │   └── TarefaDAO.java
│   │       ├── db/
│   │       │   └── Conexao.java
│   │       ├── exceptions/
│   │       │   └── TarefaNaoEncontradaException.java
│   │       ├── model/
│   │       │   ├── Tarefa.java
│   │       │   └── TarefaPrioritaria.java
│   │       ├── repository/
│   │       │   └── RepositorioGenerico.java
│   │       ├── service/
│   │       │   └── TarefaService.java
│   │       └── utils/
│   │           └── ValidadorEntrada.java
│   └── resources/
│       └── br/com/charles/taskmanager/view/
│           ├── tarefa-view.fxml
│           └── style.css
└── test/
    └── java/
        └── br/com/charles/taskmanager/
            ├── dao/
            │   └── TarefaDAOTest.java
            ├── model/
            │   └── TarefaTest.java
            ├── repository/
            │   └── RepositorioGenericoTest.java
            └── utils/
                └── ValidadorEntradaTest.java
```

---

## Camadas do Sistema

### Model

Contém as classes que representam as tarefas do sistema.

- `Tarefa`
- `TarefaPrioritaria`

A classe `TarefaPrioritaria` herda de `Tarefa`, demonstrando o uso de herança.

---

### DAO

A classe `TarefaDAO` é responsável pela persistência dos dados no banco SQLite.

Ela usa:

- `Connection`
- `PreparedStatement`
- `ResultSet`
- operações de insert, select, update e delete

---

### Service

A classe `TarefaService` centraliza as regras de negócio.

Ela valida os dados antes de enviar para o DAO.

---

### Controller

A classe `TarefaController` controla os eventos da tela JavaFX.

Ela faz a ponte entre a interface gráfica e a camada de serviço.

---

### Repository Generics

A classe `RepositorioGenerico<T>` demonstra o uso de Generics.

Ela utiliza:

- tipo genérico `<T>`;
- lista genérica `List<T>`;
- curinga `? extends T`;
- curinga `? super T`.

---

## Banco de Dados

O sistema utiliza SQLite.

O arquivo de banco é criado automaticamente com o nome:

```text
taskmanager.db
```

A tabela criada é:

```sql
CREATE TABLE IF NOT EXISTS tarefas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    titulo TEXT NOT NULL,
    descricao TEXT NOT NULL,
    concluida INTEGER NOT NULL DEFAULT 0,
    prioridade TEXT
);
```

---

## Como Executar pelo NetBeans

No NetBeans, use:

```text
Run Project
```

O arquivo `nbactions.xml` foi configurado para executar a aplicação JavaFX.

---

## Como Executar pelo Maven

No PowerShell, dentro da pasta do projeto:

```powershell
& "C:\Program Files\NetBeans-25\netbeans\java\maven\bin\mvn.cmd" javafx:run
```

---

## Como Rodar os Testes

No PowerShell:

```powershell
& "C:\Program Files\NetBeans-25\netbeans\java\maven\bin\mvn.cmd" test
```

---

## Testes Implementados

O projeto possui testes automatizados com JUnit para:

| Classe de Teste | Objetivo |
|---|---|
| `TarefaTest` | Testar criação e conclusão de tarefa |
| `ValidadorEntradaTest` | Testar validação de campos obrigatórios |
| `RepositorioGenericoTest` | Testar uso de Generics |
| `TarefaDAOTest` | Testar persistência com SQLite em memória |

---

## Principais Evoluções

### Versão Console

A primeira versão do projeto foi feita via console, com menu textual para:

- cadastrar tarefa;
- listar tarefas;
- concluir tarefa;
- remover tarefa.

A classe principal da versão console é:

```text
br.com.charles.taskmanager.app.App
```

---

### Versão JavaFX

A versão intermediária evoluiu o projeto para interface gráfica usando JavaFX.

A classe principal da versão visual é:

```text
br.com.charles.taskmanager.app.MainApplication
```

---

## Branches

| Branch | Descrição |
|---|---|
| `main` | Versão inicial console |
| `intermediario-javafx-jdbc` | Versão intermediária com JavaFX, JDBC, SQLite, Generics e JUnit |

---

## Autor

Charles Ricardo Nascimento D' Lima

Projeto acadêmico desenvolvido para a formação Capacita iRede.
