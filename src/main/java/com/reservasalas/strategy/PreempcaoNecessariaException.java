package com.reservasalas.strategy;

import com.reservasalas.model.Reserva;

/**
 * Exceção lançada pela PoliticaPrioridadeDocente quando uma reserva de estudante
 * deve ser cancelada (preemptada) para dar lugar a um docente.
 * O ReservaService intercepta essa exceção e executa o cancelamento com notificação.
 */
public class PreempcaoNecessariaException extends RuntimeException {
    private final Reserva reservaPreemptada;

    public PreempcaoNecessariaException(Reserva reservaPreemptada, String mensagem) {
        super(mensagem);
        this.reservaPreemptada = reservaPreemptada;
    }

    public Reserva getReservaPreemptada() { return reservaPreemptada; }
}
