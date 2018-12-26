package br.com.alisson.ceep.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import br.com.alisson.ceep.ui.activity.TipoListaLayout;

public class ListaNotasPreferences {

    private final static String LISTA_PREFERENCES = "br.com.alisson.ceep.preferences.ListaNotasPreferences";
    private final static String LAYOUT_PREFERENCES = "LAYOUT_PREFERENCES";

    private Context context;

    public ListaNotasPreferences(Context context) {
        this.context = context;
    }

    private SharedPreferences getSp(){
        return context.getSharedPreferences(LISTA_PREFERENCES, Activity.MODE_PRIVATE);
    }

    public String getTipoLayout(){
        return getSp().getString(LAYOUT_PREFERENCES, TipoListaLayout.GRID_LAYOUT.name());
    }

    public void setTipoLayout(String tipo){

        SharedPreferences.Editor editor = getSp().edit();
        editor.putString(LAYOUT_PREFERENCES, tipo);
        editor.apply();

    }
}
