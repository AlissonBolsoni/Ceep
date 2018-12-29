package br.com.alisson.ceep.model;

import java.io.Serializable;

public class Nota implements Serializable {

    private final String titulo;
    private final String descricao;
    private final Cor cor;

    public Nota(String titulo, String descricao, Cor cor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.cor = cor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Cor getCor() {
        return cor;
    }
}