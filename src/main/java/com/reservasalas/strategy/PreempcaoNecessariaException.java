package com.reservasalas.strategy;
import com.reservasalas.model.Reserva;
public class PreempcaoNecessariaException extends RuntimeException {
    private final Reserva reservaPreemptada;

    public PreempcaoNecessariaException(Reserva reservaPreemptada, String mensagem) {
        super(mensagem);
        this.reservaPreemptada = reservaPreemptada;
    }

    public Reserva getReservaPreemptada() { return reservaPreemptada; }
}
