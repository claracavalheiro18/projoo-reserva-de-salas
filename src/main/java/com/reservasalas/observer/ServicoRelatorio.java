package com.reservasalas.observer;

import com.reservasalas.model.Reserva;
import com.reservasalas.repository.ReservaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Observer concreto: serviço de relatório.
 * Usa modo PULL para buscar todas as reservas do dia no repositório após receber o evento.
 * RF-05: Disponibilizar relatório diário com as reservas confirmadas de cada sala.
 */
public class ServicoRelatorio implements ReservaObserver {
    private final ReservaRepository repository;

    public ServicoRelatorio(ReservaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onReservaEvento(EventoReserva evento, Reserva reserva, String mensagem) {
        // Modo PULL: busca dados atualizados do repositório
        System.out.printf("%n  📋 [RELATÓRIO - atualização automática após evento %s]%n", evento);
        gerarRelatorioHoje();
    }

    public void gerarRelatorioHoje() {
        LocalDate hoje = LocalDate.now();
        // Pull: busca diretamente no repositório
        List<Reserva> reservasHoje = repository.buscarPorData(hoje);

        System.out.println("  ══════════════════════════════════════════");
        System.out.println("  RELATÓRIO DIÁRIO – " + hoje);
        System.out.println("  ══════════════════════════════════════════");
        if (reservasHoje.isEmpty()) {
            System.out.println("  Nenhuma reserva confirmada para hoje.");
        } else {
            reservasHoje.stream()
                    .filter(Reserva::isAtiva)
                    .forEach(r -> System.out.printf(
                            "  • [%s] %s | %s → %s | Usuário: %s%n",
                            r.getSala().getTipo(), r.getSala().getNome(),
                            r.getInicio().toLocalTime(), r.getFim().toLocalTime(),
                            r.getUsuario().getNome()));
        }
        System.out.println("  ══════════════════════════════════════════");
    }

    @Override
    public String getNome() { return "ServicoRelatorio"; }
}
