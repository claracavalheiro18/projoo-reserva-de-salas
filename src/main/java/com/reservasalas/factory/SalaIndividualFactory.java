package com.reservasalas.factory;

import com.reservasalas.model.*;

public class SalaIndividualFactory extends SalaFactory {
    @Override
    protected Sala fabricar(String id, String nome, int capacidade, String localizacao, String extra) {
        return new SalaEstudoIndividual(id, nome, localizacao);
    }
}
