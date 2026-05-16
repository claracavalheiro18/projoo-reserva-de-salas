package com.reservasalas.ui;

import com.reservasalas.factory.*;
import com.reservasalas.model.*;
import com.reservasalas.observer.NotificadorUsuario;
import com.reservasalas.observer.ServicoRelatorio;
import com.reservasalas.repository.ReservaRepository;

import java.util.Arrays;
import java.util.List;

/**
 * Popula o repositório com dados iniciais de demonstração.
 */
public class DataSeeder {

    private static final List<Usuario> USUARIOS = List.of(
            new Usuario("U1", "Ana Souza",      "ana@uni.edu",      Usuario.Perfil.ESTUDANTE),
            new Usuario("U2", "Bruno Lima",     "bruno@uni.edu",    Usuario.Perfil.ESTUDANTE),
            new Usuario("U3", "Prof. Carla",    "carla@uni.edu",    Usuario.Perfil.DOCENTE),
            new Usuario("U4", "Prof. Daniel",   "daniel@uni.edu",   Usuario.Perfil.DOCENTE)
    );

    public static List<Usuario> usuarios() { return USUARIOS; }

    public static void popular(ReservaRepository repo) {
        // ── Salas via Factory Method ──────────────────────────────────────────
        SalaFactory indFac  = new SalaIndividualFactory();
        SalaFactory grpFac  = new SalaGrupoFactory();
        SalaFactory labFac  = new SalaLaboratorioFactory();

        repo.adicionarSala(indFac.criarSala("I01", "Cabine 01", 1, "Bloco A – Térreo",   null));
        repo.adicionarSala(indFac.criarSala("I02", "Cabine 02", 1, "Bloco A – Térreo",   null));
        repo.adicionarSala(grpFac.criarSala("G01", "Sala Alfa",  8, "Bloco B – 1º Andar", null));
        repo.adicionarSala(grpFac.criarSala("G02", "Sala Beta", 12, "Bloco B – 2º Andar", null));
        repo.adicionarSala(labFac.criarSala("L01", "Lab Inf",   20, "Bloco C – Térreo",   "Informática"));
        repo.adicionarSala(labFac.criarSala("L02", "Lab Quim",  16, "Bloco C – 1º Andar", "Química"));

        // ── Observers ─────────────────────────────────────────────────────────
        ServicoRelatorio relatorio = new ServicoRelatorio(repo);
        repo.assinar(relatorio);
        USUARIOS.forEach(u -> repo.assinar(new NotificadorUsuario(u)));
    }
}
