# diagramaa de reserva das salas 
┌─────────────────────────────────────────────────────────────────────────┐
│                         FACTORY METHOD                                  │
│                                                                         │
│  <<abstract>>                                                           │
│  SalaFactory                                                            │
│  ─────────────                                                          │
│  + criarSala(...) : Sala  ◄── Template Method                          │
│  # fabricar(...)  : Sala  ◄── Hook (abstrato)                          │
│        △                                                                │
│        │                                                                │
│   ┌────┴────────────────────┐                                           │
│   │                         │                                           │
│ SalaIndividualFactory  SalaGrupoFactory  SalaLaboratorioFactory         │
│                                                                         │
│  <<abstract>>                                                           │
│  Sala                                                                   │
│  ─────────────                                                          │
│  - id, nome, capacidade, localizacao                                    │
│  + getTipo() : String  (abstrato)                                       │
│        △                                                                │
│   ┌────┴──────────────────────────┐                                     │
│ SalaEstudoIndividual  SalaTrabalhoGrupo  SalaLaboratorio                │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                            STRATEGY                                     │
│                                                                         │
│  <<interface>>                                                          │
│  PoliticaDeReserva                                                      │
│  ─────────────────                                                      │
│  + validar(sala, usuario, inicio, fim, reservas, alvo) : void           │
│  + getNome() : String                                                   │
│        △                                                                │
│   ┌────┴───────────────────┐                                            │
│ PoliticaPrimeiroAReservar  PoliticaPrioridadeDocente                    │
│                                                                         │
│  ReservaService ────────────► PoliticaDeReserva (composição)           │
│  - setPolitica(p) : void    ← troca em runtime                         │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                            OBSERVER                                     │
│                                                                         │
│  <<interface>>              <<interface>>                               │
│  ReservaSubject             ReservaObserver                             │
│  ──────────────             ───────────────                             │
│  + assinar(obs)             + onReservaEvento(...)  ← PUSH             │
│  + desassinar(obs)          + getNome()                                 │
│  + notificar(...)                                                       │
│        △                          △                                     │
│        │                    ┌─────┴──────────────┐                     │
│  ReservaRepository     NotificadorUsuario    ServicoRelatorio           │
│  (implementa ambos)         (push)           (pull via repo)            │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                           SINGLETON                                     │
│                                                                         │
│  ReservaRepository                                                      │
│  ──────────────────                                                     │
│  - instancia : ReservaRepository  (static, volatile)                   │
│  + getInstance() : ReservaRepository  (double-checked locking)          │
│  - salas    : ConcurrentHashMap                                         │
│  - reservas : ConcurrentHashMap                                         │
│  - observers: List (synchronized)                                       │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                          DECORATOR (bônus)                              │
│                                                                         │
│  ReservaDecorator                                                       │
│  ─────────────────                                                      │
│  # reserva : Reserva                                                    │
│  + getDescricaoExtras() : String                                        │
│  + getCustoAdicional()  : double                                        │
│        △                                                                │
│   ┌────┴──────────┐                                                     │
│ ComMultimidia   ComLimpeza                                              │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                         MODELO DE DOMÍNIO                               │
│                                                                         │
│  Usuario                    Reserva                                     │
│  ────────                   ───────                                     │
│  - id, nome, email          - id, sala, usuario                        │
│  - perfil: {ESTUDANTE,      - inicio, fim : LocalDateTime              │
│             DOCENTE}        - status: {CONFIRMADA,                     │
│                               MODIFICADA, CANCELADA}                   │
│                             + colideWith(ini, fim) : boolean            │
└─────────────────────────────────────────────────────────────────────────┘
