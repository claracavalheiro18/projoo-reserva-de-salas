package com.reservasalas.visitor;

import java.util.ArrayList;
import com.reservasalas.model.Reserva;
import com.reservasalas.service.ReservaService;
import java.util.List;

public class RecurrenceVisitor implements IReservaVisitor {

    @Override
    public List<List<String>> visit(ReservaService service, Reserva reserva, RecurrenceRule rule) {
        List<String> successfullCreated = new ArrayList<>();
        List<String> failed = new ArrayList<>();

        switch (rule.getFrequency()) {
            case WEEKLY:
                for (int i = 1; i < rule.getOcurrences(); i++) {
                    try {
                        reserva = service.criarReserva(
                                reserva.getSala(),
                                reserva.getUsuario(),
                                reserva.getInicio().plusDays((long) i * 7 * rule.getInterval()),
                                reserva.getFim().plusDays((long) i * 7 * rule.getInterval()));
                        successfullCreated.add("Sala: " + reserva.getSala().getNome()
                                + " | Inicio: " + reserva.getInicio()
                                + " | Fim: " + reserva.getFim());
                    } catch (Exception e) {
                        failed.add("Sala: " + reserva.getSala().getNome()
                                + " | Inicio: " + reserva.getInicio().plusDays((long) i * 7 * rule.getInterval())
                                + " | Fim: " + reserva.getFim().plusDays((long) i * 7 * rule.getInterval())
                                + " | Erro: " + e.getMessage());
                    }
                }
                break;

            case MONTHLY:
                for (int i = 1; i < rule.getOcurrences(); i++) {
                    try {
                        reserva = service.criarReserva(
                                reserva.getSala(),
                                reserva.getUsuario(),
                                reserva.getInicio().plusDays((long) i * 30 * rule.getInterval()),
                                reserva.getFim().plusDays((long) i * 30 * rule.getInterval()));
                        successfullCreated.add("Sala: " + reserva.getSala().getNome()
                                + " | Inicio: " + reserva.getInicio()
                                + " | Fim: " + reserva.getFim());
                    } catch (Exception e) {
                        failed.add("Sala: " + reserva.getSala().getNome()
                                + " | Inicio: " + reserva.getInicio().plusDays((long) i * 30 * rule.getInterval())
                                + " | Fim: " + reserva.getFim().plusDays((long) i * 30 * rule.getInterval())
                                + " | Erro: " + e.getMessage());
                    }
                }
                break;

            default:
                break;
        }

        List<List<String>> result = new ArrayList<>();
        result.add(successfullCreated);
        result.add(failed);
        return result;
    }
}
