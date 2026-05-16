package com.reservasalas.model;

public class SalaEstudoIndividual extends Sala {

    public SalaEstudoIndividual(String id, String nome, String localizacao) {
        super(id, nome, 1, localizacao);
    }

    @Override
    public String getTipo() {
        return "INDIVIDUAL";
    }
}
