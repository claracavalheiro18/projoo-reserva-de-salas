package com.reservasalas.decorator;

import com.reservasalas.model.Reserva;

/** Decorator concreto: adiciona serviço de limpeza antes da reserva. */
public class ComLimpeza extends ReservaDecorator {

    public ComLimpeza(Reserva reserva) {
        super(reserva);
    }

    @Override
    public String getDescricaoExtras() { return "Serviço de Limpeza Pré-Uso"; }

    @Override
    public double getCustoAdicional() { return 15.00; }
}
