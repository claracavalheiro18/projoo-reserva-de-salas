package com.reservasalas.factory;
import com.reservasalas.model.*;
public class SalaLaboratorioFactory extends SalaFactory {
    @Override
    protected Sala fabricar(String id, String nome, int capacidade, String localizacao, String extra) {
        String especialidade = (extra != null && !extra.isBlank()) ? extra : "Geral";
        return new SalaLaboratorio(id, nome, capacidade, localizacao, especialidade);
    }
}
