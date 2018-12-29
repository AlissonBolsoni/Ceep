package br.com.alisson.ceep.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import br.com.alisson.ceep.ui.activity.TipoListaLayout;

public class SplashPreferences {

    private final static String SPLASH_PREFERENCES = "br.com.alisson.ceep.preferences.SplashPreferences";
    private final static String CONECTOU_UMA_VEZ = "CONECTOU_UMA_VEZ";

    private Context context;

    public SplashPreferences(Context context) {
        this.context = context;
    }

    private SharedPreferences getSp(){
        return context.getSharedPreferences(SPLASH_PREFERENCES, Activity.MODE_PRIVATE);
    }

    public boolean getConectadoUmaVez(){
        return getSp().getBoolean(CONECTOU_UMA_VEZ, false);
    }

    public void setConectadoUmaVez(){

        SharedPreferences.Editor editor = getSp().edit();
        editor.putBoolean(CONECTOU_UMA_VEZ, true);
        editor.apply();

    }
}
