package com.reservasalas;

import com.reservasalas.observer.ServicoRelatorio;
import com.reservasalas.repository.ReservaRepository;
import com.reservasalas.service.ReservaService;
import com.reservasalas.strategy.PoliticaPrimeiroAReservar;
import com.reservasalas.ui.DataSeeder;
import com.reservasalas.ui.MenuCLI;

/**
 * Ponto de entrada da aplicação Reserva de Salas de Estudo.
 *
 * Padrões implementados:
 *  • Factory Method  – SalaFactory e subclasses (SalaIndividualFactory, SalaGrupoFactory, SalaLaboratorioFactory)
 *  • Strategy        – PoliticaDeReserva (PoliticaPrimeiroAReservar, PoliticaPrioridadeDocente)
 *  • Observer        – ReservaSubject / ReservaObserver (NotificadorUsuario, ServicoRelatorio) com push e pull
 *  • Singleton       – ReservaRepository (double-checked locking, thread-safe)
 *  • Decorator       – ReservaDecorator (ComMultimidia, ComLimpeza) — extensão bônus
 */
public class Main {
    public static void main(String[] args) {
        // Singleton
        ReservaRepository repo = ReservaRepository.getInstance();

        // Popula salas e registra observers
        DataSeeder.popular(repo);

        // Serviço com política padrão (Strategy)
        ReservaService service = new ReservaService(repo, new PoliticaPrimeiroAReservar());

        // Referência ao ServicoRelatorio para exibição manual
        ServicoRelatorio relatorio = new ServicoRelatorio(repo);

        // CLI
        MenuCLI cli = new MenuCLI(repo, service, relatorio);
        cli.iniciar();
    }
}
