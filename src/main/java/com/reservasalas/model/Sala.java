package com.reservasalas.model;

public abstract class Sala {
    private final String id;
    private final String nome;
    private final int capacidade;
    private final String localizacao;

    public Sala(String id, String nome, int capacidade, String localizacao) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public int getCapacidade() { return capacidade; }
    public String getLocalizacao() { return localizacao; }

    public abstract String getTipo();

    @Override
    public String toString() {
        return String.format("[%s] %s - %s | Capacidade: %d | Local: %s",
                getTipo(), id, nome, capacidade, localizacao);
    }
}
