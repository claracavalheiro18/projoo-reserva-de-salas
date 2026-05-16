package com.reservasalas.strategy;

import com.reservasalas.model.Reserva;
import com.reservasalas.model.Sala;
import com.reservasalas.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public interface PoliticaDeReserva {

    /**
     * Verifica se é permitido criar/alterar uma reserva dado o contexto atual
     *
     * @param sala          Sala desejada
     * @param solicitante   Usuário que faz a requisição
     * @param inicio        Início do novo intervalo
     * @param fim           Fim do novo intervalo
     * @param reservasAtivas Lista de reservas ativas para aquela sala
     * @param reservaAlvo   Reserva que está sendo modificada (null se criação)
     * @throws IllegalStateException se a política rejeitar a operação
     */
    void validar(Sala sala, Usuario solicitante,
                 LocalDateTime inicio, LocalDateTime fim,
                 List<Reserva> reservasAtivas, Reserva reservaAlvo);

    String getNome();
}
