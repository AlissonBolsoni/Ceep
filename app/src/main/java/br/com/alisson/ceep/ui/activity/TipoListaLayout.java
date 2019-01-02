package br.com.alisson.ceep.ui.activity;

import android.content.Context;

import br.com.alisson.ceep.preferences.ListaNotasPreferences;

public enum TipoListaLayout {

    LINEAR_LAYOUT,
    GRID_LAYOUT;

    public static boolean ehLinear(String tipo) {
        return TipoListaLayout.valueOf(tipo) == LINEAR_LAYOUT;
    }

    public static boolean ehGrid(String tipo) {
        return TipoListaLayout.valueOf(tipo) == GRID_LAYOUT;
    }

    public static String trocaLayout(String tipo, Context context) {
        ListaNotasPreferences preferences = new ListaNotasPreferences(context);

        if (TipoListaLayout.ehLinear(tipo)) {
            preferences.setTipoLayout(TipoListaLayout.GRID_LAYOUT.name());
            return TipoListaLayout.GRID_LAYOUT.name();
        }

        preferences.setTipoLayout(TipoListaLayout.LINEAR_LAYOUT.name());
        return TipoListaLayout.LINEAR_LAYOUT.name();

    }

}
