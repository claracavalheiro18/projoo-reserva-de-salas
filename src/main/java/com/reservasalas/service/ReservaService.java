package com.reservasalas.service;

import com.reservasalas.model.Reserva;
import com.reservasalas.model.Sala;
import com.reservasalas.model.Usuario;
import com.reservasalas.observer.ReservaObserver.EventoReserva;
import com.reservasalas.repository.ReservaRepository;
import com.reservasalas.strategy.PoliticaDeReserva;
import com.reservasalas.strategy.PreempcaoNecessariaException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


 // Serviço principal de  criação, modificacão e cancelamento de reservs
 // RF-02: criar, modificar, cancelar
 // RF-03: detectar e impedir colisões 
 //RF-04: notificar envolvidos 
 
public class ReservaService {

    private final ReservaRepository repo;
    private PoliticaDeReserva politica;  

    public ReservaService(ReservaRepository repo, PoliticaDeReserva politica) {
        this.repo    = repo;
        this.politica = politica;
    }

    /** Troca a política de reserva em tempo de execução. */
    public void setPolitica(PoliticaDeReserva politica) {
        System.out.println("\n  ⚙️  Política alterada para: " + politica.getNome());
        this.politica = politica;
    }

    public PoliticaDeReserva getPolitica() { return politica; }

    public Reserva criarReserva(Sala sala, Usuario usuario, LocalDateTime inicio, LocalDateTime fim) {
        validarIntervalo(inicio, fim);
        List<Reserva> ativas = repo.buscarPorSala(sala.getId());

        try {
            politica.validar(sala, usuario, inicio, fim, ativas, null);
        } catch (PreempcaoNecessariaException pre) {
            // Docente sobrepõe estudante → cancela reserva anterior
            cancelarInterno(pre.getReservaPreemptada(), "Preemptada por docente " + usuario.getNome());
        }

        Reserva nova = new Reserva(sala, usuario, inicio, fim);
        repo.salvarReserva(nova);
        repo.notificar(EventoReserva.CRIADA, nova, "Reserva criada com sucesso.");
        return nova;
    }

    public Reserva modificarReserva(String reservaId, LocalDateTime novoInicio, LocalDateTime novoFim, Usuario solicitante) {
        Reserva reserva = buscarOuLancar(reservaId);
        validarProprietario(reserva, solicitante);
        validarIntervalo(novoInicio, novoFim);

        List<Reserva> ativas = repo.buscarPorSala(reserva.getSala().getId());

        try {
            politica.validar(reserva.getSala(), solicitante, novoInicio, novoFim, ativas, reserva);
        } catch (PreempcaoNecessariaException pre) {
            cancelarInterno(pre.getReservaPreemptada(), "Preemptada durante modificação por docente " + solicitante.getNome());
        }

        reserva.setInicio(novoInicio);
        reserva.setFim(novoFim);
        reserva.setStatus(Reserva.Status.MODIFICADA);
        repo.salvarReserva(reserva);
        repo.notificar(EventoReserva.MODIFICADA, reserva,
                String.format("Horário alterado para %s → %s", novoInicio, novoFim));
        return reserva;
    }
    public void cancelarReserva(String reservaId, Usuario solicitante) {
        Reserva reserva = buscarOuLancar(reservaId);
        validarProprietario(reserva, solicitante);
        cancelarInterno(reserva, "Cancelada pelo usuário " + solicitante.getNome());
    }

    public List<Sala> listarSalasDisponiveis(LocalDateTime inicio, LocalDateTime fim) {
        return repo.listarSalasDisponiveis(inicio, fim);
    }

    public List<Reserva> relatorioReservasDia(LocalDate data) {
        return repo.buscarPorData(data);
    }

    private void cancelarInterno(Reserva reserva, String motivo) {
        reserva.setStatus(Reserva.Status.CANCELADA);
        repo.salvarReserva(reserva);
        repo.notificar(EventoReserva.CANCELADA, reserva, motivo);
    }

    private Reserva buscarOuLancar(String id) {
        return repo.buscarReservaPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada: " + id));
    }

    private void validarProprietario(Reserva reserva, Usuario solicitante) {
        if (!reserva.getUsuario().getId().equals(solicitante.getId()) && !solicitante.isDocente()) {
            throw new SecurityException("Usuário " + solicitante.getNome() + " não tem permissão sobre essa reserva.");
        }
    }

    private void validarIntervalo(LocalDateTime inicio, LocalDateTime fim) {
        if (!inicio.isBefore(fim)) {
            throw new IllegalArgumentException("O horário de início deve ser anterior ao fim.");
        }
    }
}
