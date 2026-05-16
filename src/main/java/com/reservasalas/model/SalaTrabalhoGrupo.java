package com.reservasalas.model;

public class SalaTrabalhoGrupo extends Sala {

    public SalaTrabalhoGrupo(String id, String nome, int capacidade, String localizacao) {
        super(id, nome, capacidade, localizacao);
    }

    @Override
    public String getTipo() {
        return "GRUPO";
    }
}
