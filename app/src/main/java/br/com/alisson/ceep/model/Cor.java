package br.com.alisson.ceep.model;

import java.io.Serializable;

public class Cor implements Serializable {

    private int idCor;
    private String corHexa;

    public Cor(int idCor, String corHexa) {
        this.idCor = idCor;
        this.corHexa = corHexa;
    }

    public int getIdCor() {
        return idCor;
    }

    public String getCorHexa() {
        return corHexa;
    }
}
