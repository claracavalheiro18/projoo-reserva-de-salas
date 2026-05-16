package com.reservasalas.factory;
import com.reservasalas.model.*;
public class SalaGrupoFactory extends SalaFactory {
    @Override
    protected Sala fabricar(String id, String nome, int capacidade, String localizacao, String extra) {
        return new SalaTrabalhoGrupo(id, nome, capacidade, localizacao);
    }
}
