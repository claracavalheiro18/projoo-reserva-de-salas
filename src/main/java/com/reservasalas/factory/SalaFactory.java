package com.reservasalas.factory;

import com.reservasalas.model.Sala; //subclasse define cada instancia
public abstract class SalaFactory {
    public final Sala criarSala(String id, String nome, int capacidade, String localizacao, String extra) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID da sala não pode ser vazio.");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome da sala não pode ser vazio.");
        return fabricar(id, nome, capacidade, localizacao, extra);
    }
    protected abstract Sala fabricar(String id, String nome, int capacidade, String localizacao, String extra);
} //fabricas 
