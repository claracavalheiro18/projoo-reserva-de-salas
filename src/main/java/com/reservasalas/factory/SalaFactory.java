package com.reservasalas.factory;

import com.reservasalas.model.Sala;

/**
 * Factory Method – define o contrato para criação de Salas.
 * Cada subclasse concreta decide qual tipo de Sala instanciar.
 */
public abstract class SalaFactory {

    /** Template Method: valida e delega a criação concreta. */
    public final Sala criarSala(String id, String nome, int capacidade, String localizacao, String extra) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID da sala não pode ser vazio.");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome da sala não pode ser vazio.");
        return fabricar(id, nome, capacidade, localizacao, extra);
    }

    /** Hook a ser implementado pelas fábricas concretas. */
    protected abstract Sala fabricar(String id, String nome, int capacidade, String localizacao, String extra);
}
