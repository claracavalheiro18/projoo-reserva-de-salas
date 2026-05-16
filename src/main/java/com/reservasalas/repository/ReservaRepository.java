package com.reservasalas.repository;
import com.reservasalas.model.Reserva;
import com.reservasalas.model.Sala;
import com.reservasalas.observer.ReservaObserver;
import com.reservasalas.observer.ReservaSubject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ReservaRepository implements ReservaSubject {
    private static volatile ReservaRepository instancia;

    private ReservaRepository() {}

    /**
     * @return
     */
    public static ReservaRepository getInstance() {
        if (instancia == null) {
            synchronized (ReservaRepository.class) {
                if (instancia == null) {
                    instancia = new ReservaRepository();
                }
            }
        }
        return instancia;
    }
    private final Map<String, Sala>    salas    = new ConcurrentHashMap<>();
    private final Map<String, Reserva> reservas = new ConcurrentHashMap<>();
    private final List<ReservaObserver> observers = Collections.synchronizedList(new ArrayList<>());
    public void adicionarSala(Sala sala) {
        salas.put(sala.getId(), sala);
    }

    public Optional<Sala> buscarSalaPorId(String id) {
        return Optional.ofNullable(salas.get(id));
    }

    public List<Sala> listarTodasSalas() {
        return new ArrayList<>(salas.values());
    }

    /** RF-01 – Salas sem nenhuma reserva ativa no intervalo fornecido. */
    public List<Sala> listarSalasDisponiveis(LocalDateTime inicio, LocalDateTime fim) {
        Set<String> ocupadas = reservas.values().stream()
                .filter(Reserva::isAtiva)
                .filter(r -> r.colideWith(inicio, fim))
                .map(r -> r.getSala().getId())
                .collect(Collectors.toSet());

        return salas.values().stream()
                .filter(s -> !ocupadas.contains(s.getId()))
                .collect(Collectors.toList());
    }

    // ── Reservas ──────────────────────────────────────────────────────────────
    public void salvarReserva(Reserva reserva) {
        reservas.put(reserva.getId(), reserva);
    }

    public Optional<Reserva> buscarReservaPorId(String id) {
        return Optional.ofNullable(reservas.get(id));
    }

    public List<Reserva> listarTodasReservas() {
        return new ArrayList<>(reservas.values());
    }

    public List<Reserva> buscarPorSala(String salaId) {
        return reservas.values().stream()
                .filter(r -> r.getSala().getId().equals(salaId))
                .collect(Collectors.toList());
    }

    /** RF-05 – Reservas com início no dia informado. */
    public List<Reserva> buscarPorData(LocalDate data) {
        return reservas.values().stream()
                .filter(r -> r.getInicio().toLocalDate().equals(data))
                .sorted(Comparator.comparing(Reserva::getInicio))
                .collect(Collectors.toList());
    }
    @Override
    public void assinar(ReservaObserver observer) {
        observers.add(observer);
    }

    @Override
    public void desassinar(ReservaObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notificar(ReservaObserver.EventoReserva evento, Reserva reserva, String mensagem) {
        List<ReservaObserver> copia;
        synchronized (observers) {
            copia = new ArrayList<>(observers);
        }
        for (ReservaObserver obs : copia) {
            obs.onReservaEvento(evento, reserva, mensagem);
        }
    }

    //  observers pra uso interno pelo serviço
    public List<ReservaObserver> getObservers() {
        synchronized (observers) {
            return new ArrayList<>(observers);
        }
    }
}
