# Reserva de Salas de Estudo

Aplicação de gerenciamento de reservas de salas para um campus universitário.
Projeto prático – Projeto de Software Orientado a Objetos

---

## Autores

| Nome                      | GitHub / Contato                                                             |
| ------------------------- | ---------------------------------------------------------------------------- |
| Clara Cavalheiro          | [clara.cavalheiro@unifesp.br](mailto:clara.cavalheiro@unifesp.br) |
| Davi de Oliveira Custódio | [d.custodio@unifesp.br](mailto:d.custodio@unifesp.br)                        |

---

## Requisitos Funcionais

| Código | Requisito                                                             |
| ------ | --------------------------------------------------------------------- |
| RF-01  | Listar salas disponíveis em um intervalo de datas                     |
| RF-02  | Criar, modificar ou cancelar uma reserva                              |
| RF-03  | Detectar e impedir colisões de horário                                |
| RF-04  | Notificação aos envolvidos quando a reserva for alterada ou cancelada |
| RF-05  | Relatório diário com reservas confirmadas                             |

---

## Padrões de Projeto

* Factory Method
* Strategy
* Observer
* Singleton
* Decorator

---

## Estrutura do Projeto

```text
src/
└── main/java/com/reservasalas/
    ├── Main.java
    ├── model/
    ├── factory/
    ├── strategy/
    ├── observer/
    ├── repository/
    ├── decorator/
    ├── service/
    └── ui/

docs/
└── diagrama-classes.md

README.md
```

---

## Como Executar

### Pré-requisitos

* Java 17+

### Terminal

```bash
find src -name "*.java" > sources.txt
javac -d out @sources.txt

java -cp out com.reservasalas.Main
```

### VS Code

1. Instale a extensão Extension Pack for Java.
2. Abra a pasta do projeto.
3. Execute o arquivo `Main.java`.

---

## Funcionalidades

```text
1. Listar salas disponíveis
2. Criar reserva
3. Modificar reserva
4. Cancelar reserva
5. Relatório do dia
6. Trocar política de reserva
7. Trocar usuário logado
8. Demo Decorator
```

---

## Dados de Demonstração

### Usuários

* Ana Souza – Estudante
* Bruno Lima – Estudante
* Prof. Carla – Docente
* Prof. Daniel – Docente

### Salas

* I01, I02 — Cabines individuais
* G01, G02 — Salas de grupo
* L01 — Laboratório de Informática
* L02 — Laboratório de Química

