package com.reservasalas.decorator;

import com.reservasalas.model.Reserva; // add limpeza 
public class ComLimpeza extends ReservaDecorator {

    public ComLimpeza(Reserva reserva) {
        super(reserva);
    }

    @Override
    public String getDescricaoExtras() { return "Serviço de Limpeza Pré-Uso"; }

    @Override
    public double getCustoAdicional() { return 15.00; }
}
