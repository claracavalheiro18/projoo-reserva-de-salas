package com.reservasalas;

import com.reservasalas.observer.ServicoRelatorio;
import com.reservasalas.repository.ReservaRepository;
import com.reservasalas.service.ReservaService;
import com.reservasalas.strategy.PoliticaPrimeiroAReservar;
import com.reservasalas.ui.DataSeeder;
import com.reservasalas.ui.MenuCLI;


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
