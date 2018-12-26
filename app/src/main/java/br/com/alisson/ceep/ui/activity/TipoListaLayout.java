package br.com.alisson.ceep.ui.activity;

public enum TipoListaLayout {

    LINEAR_LAYOUT,
    GRID_LAYOUT;

    public static boolean ehLinear(String tipo){
        return TipoListaLayout.valueOf(tipo) == LINEAR_LAYOUT;
    }

    public static boolean ehGrid(String tipo){
        return TipoListaLayout.valueOf(tipo) == GRID_LAYOUT;
    }

}
