package br.com.alisson.ceep.dao;

import java.util.ArrayList;

import br.com.alisson.ceep.R;
import br.com.alisson.ceep.model.Cor;

public class CorDao {

    public ArrayList<Cor> listaCores(){

        ArrayList<Cor> cores = new ArrayList<>();

        cores.add(new Cor(R.drawable.branco, "#FFFFFF"));
        cores.add(new Cor(R.drawable.azul, "#408EC9"));
        cores.add(new Cor(R.drawable.amarelo, "#F9F256"));
        cores.add(new Cor(R.drawable.vermelho, "#EC2F4B"));
        cores.add(new Cor(R.drawable.verde, "#9ACD32"));
        cores.add(new Cor(R.drawable.lilas, "#F1CBFF"));
        cores.add(new Cor(R.drawable.cinza, "#D2D4DC"));
        cores.add(new Cor(R.drawable.marrom, "#A47C48"));
        cores.add(new Cor(R.drawable.roxo, "#BE29EC"));

        return cores;

    }

    public Cor CorPadrao(){return new Cor(R.drawable.branco, "#FFFFFF");}

}
