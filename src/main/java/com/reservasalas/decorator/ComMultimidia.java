package com.reservasalas.decorator;

import com.reservasalas.model.Reserva; // add os materiais da sal
public class ComMultimidia extends ReservaDecorator {
    private final String equipamento;

    public ComMultimidia(Reserva reserva, String equipamento) {
        super(reserva);
        this.equipamento = equipamento;
    }

    @Override
    public String getDescricaoExtras() { return "Multimídia[" + equipamento + "]"; }

    @Override
    public double getCustoAdicional() { return 30.00; }
}
