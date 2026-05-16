package com.reservasalas.strategy;

import com.reservasalas.model.Reserva;
import com.reservasalas.model.Sala;
import com.reservasalas.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Política "Prioridade para Docente".
 * - Docente pode sobrepor reserva de estudante (a reserva anterior é cancelada pelo serviço).
 * - Estudante NÃO pode sobrepor reserva de docente nem de outro estudante.
 * - Docente NÃO pode sobrepor reserva de outro docente.
 */
public class PoliticaPrioridadeDocente implements PoliticaDeReserva {

    @Override
    public void validar(Sala sala, Usuario solicitante,
                        LocalDateTime inicio, LocalDateTime fim,
                        List<Reserva> reservasAtivas, Reserva reservaAlvo) {

        for (Reserva r : reservasAtivas) {
            if (reservaAlvo != null && r.getId().equals(reservaAlvo.getId())) continue;
            if (!r.isAtiva() || !r.getSala().getId().equals(sala.getId())) continue;
            if (!r.colideWith(inicio, fim)) continue;

            boolean solicitanteEhDocente = solicitante.isDocente();
            boolean titularEhDocente     = r.getUsuario().isDocente();

            if (!solicitanteEhDocente) {
                // Estudante nunca pode sobrepor
                throw new IllegalStateException(
                        String.format("Conflito: sala '%s' já reservada por %s (%s). " +
                                        "Estudantes não possuem prioridade para sobrepor reservas.",
                                sala.getNome(), r.getUsuario().getNome(), r.getUsuario().getPerfil()));
            }

            if (titularEhDocente) {
                // Docente vs Docente → rejeita
                throw new IllegalStateException(
                        String.format("Conflito: sala '%s' já reservada pelo docente %s. " +
                                        "Docentes não podem sobrepor reservas de outros docentes.",
                                sala.getNome(), r.getUsuario().getNome()));
            }

            // Docente sobrepõe estudante → sinaliza via exceção especial para o serviço tratar
            throw new PreempcaoNecessariaException(r,
                    "Docente " + solicitante.getNome() + " solicitou a sala — reserva de estudante será cancelada.");
        }
    }

    @Override
    public String getNome() { return "Prioridade para Docente"; }
}
