package com.example.rajfilms;

import java.io.Serializable;

public class Comentario implements Serializable {
    private int idFilme;
    private String tituloFilme;
    private String comentario;
    private String userNome;
    private String dataComentario;

    public Comentario() {}

    public Comentario(int idFilme, String tituloFilme, String comentario, String userNome, String dataComentario) {
        this.idFilme = idFilme;
        this.tituloFilme = tituloFilme;
        this.comentario = comentario;
        this.userNome = userNome;
        this.dataComentario = dataComentario;
    }

    public int getIdFilme() {
        return idFilme;
    }

    public String getTituloFilme() {
        return tituloFilme;
    }

    public String getComentario() {
        return comentario;
    }

    public String getUserNome() {
        return userNome;
    }

    public String getDataComentario() {
        return dataComentario;
    }

}