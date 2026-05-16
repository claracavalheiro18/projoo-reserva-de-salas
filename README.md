# 📚 Reserva de Salas de Estudo

Aplicação de gerenciamento de reservas de salas para um campus universitário.  
Projeto prático – **Projeto de Software Orientado a Objetos** – 2026-05-08.

---

## 👥 Autores

| Nome | GitHub |
|------|--------|
| Clara Cavalheiro | [@claracavalheiro18](https://github.com/claracavalheiro18) |

---

## 📋 Requisitos Funcionais

| Código | Requisito | Status |
|--------|-----------|--------|
| RF-01 | Listar salas disponíveis em um intervalo de datas | ✅ |
| RF-02 | Criar, modificar ou cancelar uma reserva | ✅ |
| RF-03 | Detectar e impedir colisões de horário | ✅ |
| RF-04 | Notificação imediata aos envolvidos quando reserva for alterada/cancelada | ✅ |
| RF-05 | Relatório diário com reservas confirmadas de cada sala | ✅ |

---

## 🏗️ Padrões de Projeto Implementados

### Factory Method
- **`SalaFactory`** (abstrata) → `SalaIndividualFactory`, `SalaGrupoFactory`, `SalaLaboratorioFactory`
- Instancia os três subtipos de `Sala` sem acoplamento à classe concreta.

### Strategy
- **`PoliticaDeReserva`** (interface) → `PoliticaPrimeiroAReservar`, `PoliticaPrioridadeDocente`
- Trocável em tempo de execução via `ReservaService.setPolitica(...)`.

### Observer
- **`ReservaSubject`** / **`ReservaObserver`**
- Implementações: `NotificadorUsuario` (push), `ServicoRelatorio` (pull via repositório).
- Notificações disparadas em criação, modificação e cancelamento.

### Singleton
- **`ReservaRepository`** — repositório central em memória, thread-safe (double-checked locking).

### Decorator *(bônus)*
- **`ReservaDecorator`** → `ComMultimidia`, `ComLimpeza`
- Adiciona funcionalidades extras às reservas de forma transparente.

---

## 📁 Estrutura do Repositório

```
src/
└── main/java/com/reservasalas/
    ├── Main.java
    ├── model/          # Sala, Reserva, Usuario e subtipos de Sala
    ├── factory/        # SalaFactory e fábricas concretas
    ├── strategy/       # PoliticaDeReserva e implementações
    ├── observer/       # ReservaObserver, ReservaSubject e implementações
    ├── singleton/      # (incorporado em repository/)
    ├── repository/     # ReservaRepository (Singleton)
    ├── decorator/      # ReservaDecorator e decoradores concretos
    ├── service/        # ReservaService (lógica de negócio)
    └── ui/             # MenuCLI, DataSeeder
docs/
└── diagrama-classes.md
README.md
```

---

## ▶️ Como Executar

### Pré-requisitos
- Java 17+ instalado

### Via terminal

```bash
# Compile (a partir da raiz do projeto)
find src -name "*.java" > sources.txt
javac -d out @sources.txt

# Execute
java -cp out com.reservasalas.Main
```

### Via VS Code
1. Instale a extensão **Extension Pack for Java** (Microsoft).
2. Abra a pasta `reserva-salas` no VS Code.
3. Abra `Main.java` e clique em **Run** (▶️) acima do método `main`.

---

## 🖥️ Funcionalidades do Menu

```
1. Listar salas disponíveis       → RF-01
2. Criar reserva                  → RF-02 + RF-03 + RF-04
3. Modificar reserva              → RF-02 + RF-03 + RF-04
4. Cancelar reserva               → RF-02 + RF-04
5. Relatório do dia               → RF-05
6. Trocar política de reserva     → Strategy em runtime
7. Trocar usuário logado
8. Demo Decorator (extras)        → Bônus
```

---

## 🧪 Dados de Demonstração (pré-carregados)

**Usuários:**
- Ana Souza – Estudante
- Bruno Lima – Estudante
- Prof. Carla – Docente
- Prof. Daniel – Docente

**Salas:**
- `I01`, `I02` — Cabines individuais (Bloco A)
- `G01`, `G02` — Salas de grupo (Bloco B)
- `L01` — Laboratório de Informática (Bloco C)
- `L02` — Laboratório de Química (Bloco C)
