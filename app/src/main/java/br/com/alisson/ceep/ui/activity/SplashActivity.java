package br.com.alisson.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.alisson.ceep.R;
import br.com.alisson.ceep.preferences.SplashPreferences;
import br.com.alisson.ceep.ui.async.SplashAsync;

public class SplashActivity extends AppCompatActivity implements SplashAsync.SplashCallback {

    private SplashPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        executaEsperaTelaSplash();
    }

    private void executaEsperaTelaSplash() {
        SplashAsync splashAsync = new SplashAsync(this);
        sp = new SplashPreferences(this);
        splashAsync.execute(sp.getConectadoUmaVez());
    }

    @Override
    public void splashCallback() {
        sp.setConectadoUmaVez();
        Intent intent = new Intent(this, ListaNotasActivity.class);
        startActivity(intent);
        finish();
    }
}
