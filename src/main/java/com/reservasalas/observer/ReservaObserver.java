package com.reservasalas.observer;

import com.reservasalas.model.Reserva;

public interface ReservaObserver {

    /**
     * Modo PUSH – chamado pelo Subject com dados completos do evento.
     *
     * @param evento     Tipo do evento ocorrido
     * @param reserva    Reserva afetada (com estado atual)
     * @param mensagem   Descrição legível do evento
     */
    void onReservaEvento(EventoReserva evento, Reserva reserva, String mensagem);

    String getNome();

    enum EventoReserva {
        CRIADA, MODIFICADA, CANCELADA
    }
}
