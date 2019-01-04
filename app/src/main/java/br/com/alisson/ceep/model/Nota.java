package br.com.alisson.ceep.model;

import java.io.Serializable;

public class Nota implements Serializable {

    public static final int ATIVO = 0;
    public static final int DESATIVADO = 1;

    private int id;
    private final String titulo;
    private final String descricao;
    private Cor cor;
    private int posicao;
    private int desativado;

    public Nota(String titulo, String descricao, Cor cor) {
        this(0,titulo, descricao, cor, 0,0);
    }

    public Nota(int id, String titulo, String descricao, Cor cor, int posicao, int desativado) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.cor = cor;
        this.posicao = posicao;
        this.desativado = desativado;
    }

    public void desativa(){this.desativado = DESATIVADO;}

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Cor getCor() {
        return cor;
    }

    public int getId() {
        return id;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public int getDesativado() {
        return desativado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }
}