package com.reservasalas.observer;
public interface ReservaSubject {
    void assinar(ReservaObserver observer);
    void desassinar(ReservaObserver observer);
    void notificar(ReservaObserver.EventoReserva evento,
                   com.reservasalas.model.Reserva reserva,
                   String mensagem);
}
