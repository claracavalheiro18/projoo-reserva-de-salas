package com.reservasalas.ui;

import com.reservasalas.decorator.ComLimpeza;
import com.reservasalas.decorator.ComMultimidia;
import com.reservasalas.decorator.ReservaDecorator;
import com.reservasalas.model.Reserva;
import com.reservasalas.model.Sala;
import com.reservasalas.model.Usuario;
import com.reservasalas.observer.ServicoRelatorio;
import com.reservasalas.repository.ReservaRepository;
import com.reservasalas.service.ReservaService;
import com.reservasalas.strategy.PoliticaPrimeiroAReservar;
import com.reservasalas.strategy.PoliticaPrioridadeDocente;
import com.reservasalas.visitor.IReservaVisitor;
import com.reservasalas.visitor.RecurrenceRule;
import com.reservasalas.visitor.RecurrenceVisitor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Interface de Linha de Comando (CLI).
 * Ponto de entrada principal da aplicação.
 */
public class MenuCLI {

    private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter FMT_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final ReservaRepository repo;
    private final ReservaService     service;
    private final ServicoRelatorio   relatorio;
    private final Scanner            sc;
    private Usuario                  usuarioLogado;

    public MenuCLI(ReservaRepository repo, ReservaService service, ServicoRelatorio relatorio) {
        this.repo      = repo;
        this.service   = service;
        this.relatorio = relatorio;
        this.sc        = new Scanner(System.in);
    }

    public void iniciar() {
        cabecalho("RESERVA DE SALAS DE ESTUDO – Campus Universitário");
        selecionarUsuario();

        boolean sair = false;
        while (!sair) {
            menuPrincipal();
            int op = lerInt("Opção");
            switch (op) {
                case 1 -> listarSalasDisponiveis();
                case 2 -> criarReserva();
                case 3 -> modificarReserva();
                case 4 -> cancelarReserva();
                case 5 -> relatorioHoje();
                case 6 -> trocarPolitica();
                case 7 -> trocarUsuario();
                case 8 -> decoratorDemo();
                case 9 -> reservaRecorrenteDemo();
                case 0 -> sair = true;
                default -> System.out.println("  ⚠  Opção inválida.");
            }
        }
        System.out.println("\n  Encerrando sistema. Até logo!");
    }

    // ── Telas ─────────────────────────────────────────────────────────────────

    private void menuPrincipal() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.printf ("│  Usuário: %-31s│%n", usuarioLogado.getNome() + " (" + usuarioLogado.getPerfil() + ")");
        System.out.printf ("│  Política: %-30s│%n", service.getPolitica().getNome());
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│  1. Listar salas disponíveis             │");
        System.out.println("│  2. Criar reserva                        │");
        System.out.println("│  3. Modificar reserva                    │");
        System.out.println("│  4. Cancelar reserva                     │");
        System.out.println("│  5. Relatório do dia                     │");
        System.out.println("│  6. Trocar política de reserva           │");
        System.out.println("│  7. Trocar usuário                       │");
        System.out.println("│  8. Demo Decorator (extras na reserva)   │");
        System.out.println("│  9. Criar reserva recorrente (Visitor)   │");
        System.out.println("│  0. Sair                                 │");
        System.out.println("└─────────────────────────────────────────┘");
    }

    private void listarSalasDisponiveis() {
        cabecalho("SALAS DISPONÍVEIS");
        System.out.print("  Data (dd/MM/yyyy) [Enter = hoje]: ");
        String dataStr = sc.nextLine().trim();
        LocalDate data = dataStr.isBlank() ? LocalDate.now() : LocalDate.parse(dataStr, FMT_DATA);

        System.out.print("  Horário início (HH:mm): ");
        LocalTime ini = LocalTime.parse(sc.nextLine().trim(), FMT_HORA);
        System.out.print("  Horário fim   (HH:mm): ");
        LocalTime fim = LocalTime.parse(sc.nextLine().trim(), FMT_HORA);

        List<Sala> disponiveis = service.listarSalasDisponiveis(
                data.atTime(ini), data.atTime(fim));

        if (disponiveis.isEmpty()) {
            System.out.println("  Nenhuma sala disponível no período.");
        } else {
            disponiveis.forEach(s -> System.out.println("  • " + s));
        }
    }

    private void criarReserva() {
        cabecalho("CRIAR RESERVA");
        Sala sala = selecionarSala();
        if (sala == null) return;

        LocalDateTime[] intervalo = lerIntervalo();
        if (intervalo == null) return;

        try {
            Reserva nova = service.criarReserva(sala, usuarioLogado, intervalo[0], intervalo[1]);
            System.out.println("\n  ✅  Reserva criada: " + nova.getId());
        } catch (Exception e) {
            System.out.println("\n  ❌  " + e.getMessage());
        }
    }

    private void modificarReserva() {
        cabecalho("MODIFICAR RESERVA");
        System.out.print("  ID da reserva: ");
        String id = sc.nextLine().trim().toUpperCase();

        LocalDateTime[] intervalo = lerIntervalo();
        if (intervalo == null) return;

        try {
            Reserva mod = service.modificarReserva(id, intervalo[0], intervalo[1], usuarioLogado);
            System.out.println("\n  ✅  Reserva modificada: " + mod.getId());
        } catch (Exception e) {
            System.out.println("\n  ❌  " + e.getMessage());
        }
    }

    private void cancelarReserva() {
        cabecalho("CANCELAR RESERVA");
        System.out.print("  ID da reserva: ");
        String id = sc.nextLine().trim().toUpperCase();
        try {
            service.cancelarReserva(id, usuarioLogado);
            System.out.println("\n  ✅  Reserva " + id + " cancelada.");
        } catch (Exception e) {
            System.out.println("\n  ❌  " + e.getMessage());
        }
    }

    private void relatorioHoje() {
        cabecalho("RELATÓRIO DIÁRIO");
        relatorio.gerarRelatorioHoje();
    }

    private void trocarPolitica() {
        System.out.println("\n  1. Primeiro a Reservar (FIFO)");
        System.out.println("  2. Prioridade para Docente");
        int op = lerInt("Escolha");
        if (op == 1)      service.setPolitica(new PoliticaPrimeiroAReservar());
        else if (op == 2) service.setPolitica(new PoliticaPrioridadeDocente());
        else              System.out.println("  Opção inválida.");
    }

    private void trocarUsuario() {
        selecionarUsuario();
    }

    private void decoratorDemo() {
        cabecalho("DEMO – DECORATOR (Extras em Reserva)");
        System.out.print("  ID da reserva para adicionar extras: ");
        String id = sc.nextLine().trim().toUpperCase();

        repo.buscarReservaPorId(id).ifPresentOrElse(r -> {
            System.out.println("  1. Equipamento Multimídia (+ R$ 30,00)");
            System.out.println("  2. Serviço de Limpeza (+ R$ 15,00)");
            System.out.println("  3. Ambos");
            int op = lerInt("Escolha");
            ReservaDecorator dec = switch (op) {
                case 1 -> new ComMultimidia(r, "Projetor + Tela");
                case 2 -> new ComLimpeza(r);
                case 3 -> new ComMultimidia(new ComLimpeza(r).getReserva(), "Projetor + Tela");
                default -> { System.out.println("Opção inválida."); yield null; }
            };
            if (dec != null) System.out.println("\n  " + dec);
        }, () -> System.out.println("  Reserva não encontrada."));
    }

    // ── Auxiliares ────────────────────────────────────────────────────────────

    private void selecionarUsuario() {
        List<Usuario> usuarios = DataSeeder.usuarios();
        cabecalho("SELECIONAR USUÁRIO");
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, usuarios.get(i));
        }
        int idx = lerInt("Escolha") - 1;
        if (idx >= 0 && idx < usuarios.size()) {
            usuarioLogado = usuarios.get(idx);
            System.out.println("  Logado como: " + usuarioLogado.getNome());
        } else {
            usuarioLogado = usuarios.get(0);
            System.out.println("  Seleção inválida. Usando: " + usuarioLogado.getNome());
        }
    }

    private Sala selecionarSala() {
        List<Sala> salas = repo.listarTodasSalas();
        salas.forEach(s -> System.out.println("  • " + s));
        System.out.print("  ID da sala: ");
        String id = sc.nextLine().trim().toUpperCase();
        return repo.buscarSalaPorId(id).orElseGet(() -> {
            System.out.println("  Sala não encontrada.");
            return null;
        });
    }

    private LocalDateTime[] lerIntervalo() {
        try {
            System.out.print("  Data (dd/MM/yyyy) [Enter = hoje]: ");
            String dataStr = sc.nextLine().trim();
            LocalDate data = dataStr.isBlank() ? LocalDate.now() : LocalDate.parse(dataStr, FMT_DATA);
            System.out.print("  Horário início (HH:mm): ");
            LocalTime ini = LocalTime.parse(sc.nextLine().trim(), FMT_HORA);
            System.out.print("  Horário fim   (HH:mm): ");
            LocalTime fim = LocalTime.parse(sc.nextLine().trim(), FMT_HORA);
            return new LocalDateTime[]{data.atTime(ini), data.atTime(fim)};
        } catch (Exception e) {
            System.out.println("  Formato inválido de data/hora.");
            return null;
        }
    }

    private int lerInt(String prompt) {
        System.out.print("  " + prompt + ": ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void cabecalho(String titulo) {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  " + titulo);
        System.out.println("══════════════════════════════════════════");
    }

    private void reservaRecorrenteDemo() {
        cabecalho("CRIAR RESERVA RECORRENTE (Visitor)");

        // 1. Seleciona a reserva base (já existente)
        System.out.print("  ID da reserva base: ");
        String id = sc.nextLine().trim().toUpperCase();

        repo.buscarReservaPorId(id).ifPresentOrElse(reservaBase -> {

            // 2. Frequência
            System.out.println("  Frequência:");
            System.out.println("    1. Semanal (WEEKLY)");
            System.out.println("    2. Mensal  (MONTHLY)");
            int freqOp = lerInt("Escolha");
            com.reservasalas.visitor.Frequency freq =
                    (freqOp == 2) ? com.reservasalas.visitor.Frequency.MONTHLY
                                  : com.reservasalas.visitor.Frequency.WEEKLY;

            // 3. Intervalo
            int intervalo = lerInt("Intervalo (1 = toda semana/mês, 2 = a cada 2, ...)");
            if (intervalo <= 0) intervalo = 1;

            // 4. Ocorrências
            int ocorrencias = lerInt("Quantas ocorrências (além da reserva base)");
            if (ocorrencias <= 0) {
                System.out.println("  ⚠  Número de ocorrências inválido.");
                return;
            }

            // 5. Executa o Visitor
            com.reservasalas.visitor.RecurrenceRule rule =
                    new com.reservasalas.visitor.RecurrenceRule(freq, intervalo, ocorrencias + 1);

            IReservaVisitor visitor = new RecurrenceVisitor();
            java.util.List<java.util.List<String>> resultado =
                    visitor.visit(service, reservaBase, rule);

            // 6. Exibe resultado
            java.util.List<String> sucessos = resultado.get(0);
            java.util.List<String> falhas   = resultado.get(1);

            System.out.println("\n  ✅  Reservas criadas com sucesso (" + sucessos.size() + "):");
            sucessos.forEach(s -> System.out.println("     • " + s));

            if (!falhas.isEmpty()) {
                System.out.println("\n  ❌  Reservas com conflito (" + falhas.size() + "):");
                falhas.forEach(f -> System.out.println("     • " + f));
            }

        }, () -> System.out.println("  Reserva não encontrada."));
    }

}