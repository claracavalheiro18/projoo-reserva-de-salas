package com.reservasalas.model;

public class Usuario {
    public enum Perfil { ESTUDANTE, DOCENTE }

    private final String id;
    private final String nome;
    private final String email;
    private final Perfil perfil;

    public Usuario(String id, String nome, String email, Perfil perfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
    }

    public String getId()    { return id; }
    public String getNome()  { return nome; }
    public String getEmail() { return email; }
    public Perfil getPerfil(){ return perfil; }

    public boolean isDocente() { return perfil == Perfil.DOCENTE; }

    @Override
    public String toString() {
        return String.format("%s (%s) <%s>", nome, perfil, email);
    }
}
