package com.reservasalas.model;

public class SalaLaboratorio extends Sala {
    private final String especialidade;

    public SalaLaboratorio(String id, String nome, int capacidade, String localizacao, String especialidade) {
        super(id, nome, capacidade, localizacao);
        this.especialidade = especialidade;
    }

    public String getEspecialidade() { return especialidade; }

    @Override
    public String getTipo() {
        return "LABORATORIO";
    }

    @Override
    public String toString() {
        return super.toString() + " | Especialidade: " + especialidade;
    }
}
