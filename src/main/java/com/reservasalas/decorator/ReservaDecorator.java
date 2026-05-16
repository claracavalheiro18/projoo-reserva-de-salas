package com.reservasalas.decorator;

import com.reservasalas.model.Reserva;//bonus do exs 
public abstract class ReservaDecorator {
    protected final Reserva reserva;

    public ReservaDecorator(Reserva reserva) {
        this.reserva = reserva;
    }

    public Reserva getReserva() { return reserva; }

    public abstract String getDescricaoExtras();

    public abstract double getCustoAdicional();

    @Override
    public String toString() {
        return reserva + " | Extras: " + getDescricaoExtras()
                + " | Custo adicional: R$" + String.format("%.2f", getCustoAdicional());
    }
}
