package com.example.rajfilms;

public class Filme {
    private int id;
    private String titulo;
    private String descricao;
    private String genero;
    private String poster;

    public Filme(){}

    public Filme(int id, String titulo, String descricao, String genero, String poster) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.genero = genero;
        this.poster = poster;
    }

    public int getId() { return id; }

    public String getPoster() { return poster; }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getGenero() {
        return genero;
    }

}
