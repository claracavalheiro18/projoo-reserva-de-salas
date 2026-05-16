package com.reservasalas.visitor;

import com.reservasalas.model.Reserva;
import com.reservasalas.service.ReservaService;
import java.util.List;

public interface IReservaVisitor {
    List<List<String>> visit(ReservaService service, Reserva reserva, RecurrenceRule rule);
}
