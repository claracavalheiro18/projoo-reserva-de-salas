package com.reservasalas.observer;
import com.reservasalas.model.Reserva;
import com.reservasalas.model.Usuario;
public class NotificadorUsuario implements ReservaObserver {
    private final Usuario usuario;

    public NotificadorUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void onReservaEvento(EventoReserva evento, Reserva reserva, String mensagem) {
        // Filtra apenas eventos que envolvem este usuário
        if (!reserva.getUsuario().getId().equals(usuario.getId())) return;

        System.out.printf("%n  📧 [NOTIFICAÇÃO → %s <%s>]%n     Evento : %s%n     Reserva: %s%n     Detalhe: %s%n",
                usuario.getNome(), usuario.getEmail(), evento, reserva.getId(), mensagem);
    }

    @Override
    public String getNome() { return "NotificadorUsuario[" + usuario.getNome() + "]"; }
}
