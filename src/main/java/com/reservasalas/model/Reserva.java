package com.reservasalas.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reserva {
    public enum Status { CONFIRMADA, CANCELADA, MODIFICADA }

    private final String id;
    private final Sala sala;
    private final Usuario usuario;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Status status;
    private final LocalDateTime criadaEm;

    public Reserva(Sala sala, Usuario usuario, LocalDateTime inicio, LocalDateTime fim) {
        this.id       = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.sala     = sala;
        this.usuario  = usuario;
        this.inicio   = inicio;
        this.fim      = fim;
        this.status   = Status.CONFIRMADA;
        this.criadaEm = LocalDateTime.now();
    }

    // Getters
    public String getId()              { return id; }
    public Sala getSala()              { return sala; }
    public Usuario getUsuario()        { return usuario; }
    public LocalDateTime getInicio()   { return inicio; }
    public LocalDateTime getFim()      { return fim; }
    public Status getStatus()          { return status; }
    public LocalDateTime getCriadaEm() { return criadaEm; }

    // Setters para modificação
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }
    public void setFim(LocalDateTime fim)        { this.fim = fim; }
    public void setStatus(Status status)         { this.status = status; }

    public boolean isAtiva() { return status == Status.CONFIRMADA; }

    /** Verifica se este intervalo colide com outro. */
    public boolean colideWith(LocalDateTime outroInicio, LocalDateTime outroFim) {
        return inicio.isBefore(outroFim) && fim.isAfter(outroInicio);
    }

    @Override
    public String toString() {
        return String.format("Reserva[%s] Sala:%s | Usuário:%s | %s → %s | Status:%s",
                id, sala.getNome(), usuario.getNome(),
                inicio, fim, status);
    }
}
