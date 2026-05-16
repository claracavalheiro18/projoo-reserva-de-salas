package com.reservasalas.strategy;

import com.reservasalas.model.Reserva;
import com.reservasalas.model.Sala;
import com.reservasalas.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Política "Primeiro a reservar" (FIFO).
 * Qualquer conflito de horário resulta em rejeição — não importa o perfil do usuário.
 */
public class PoliticaPrimeiroAReservar implements PoliticaDeReserva {

    @Override
    public void validar(Sala sala, Usuario solicitante,
                        LocalDateTime inicio, LocalDateTime fim,
                        List<Reserva> reservasAtivas, Reserva reservaAlvo) {

        for (Reserva r : reservasAtivas) {
            // Ignorar a própria reserva em caso de modificação
            if (reservaAlvo != null && r.getId().equals(reservaAlvo.getId())) continue;

            if (r.isAtiva() && r.getSala().getId().equals(sala.getId())
                    && r.colideWith(inicio, fim)) {
                throw new IllegalStateException(
                        String.format("Conflito de horário: sala '%s' já reservada por %s no período %s → %s. " +
                                        "Política: Primeiro a reservar — solicitação rejeitada.",
                                sala.getNome(), r.getUsuario().getNome(), r.getInicio(), r.getFim()));
            }
        }
    }

    @Override
    public String getNome() { return "Primeiro a Reservar (FIFO)"; }
}
