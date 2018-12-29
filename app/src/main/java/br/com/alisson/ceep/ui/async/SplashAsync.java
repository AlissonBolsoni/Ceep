package br.com.alisson.ceep.ui.async;

import android.content.Intent;
import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

import br.com.alisson.ceep.preferences.SplashPreferences;
import br.com.alisson.ceep.ui.activity.ListaNotasActivity;
import br.com.alisson.ceep.ui.activity.SplashActivity;

public class SplashAsync extends AsyncTask<Void, Void, Void> {

    private SplashActivity context;

    public SplashAsync(SplashActivity context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        SplashPreferences sp = new SplashPreferences(context);
        try {
            if (sp.getConectadoUmaVez()) {
                Thread.sleep(500);
            } else {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sp.setConectadoUmaVez();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        Intent intent = new Intent(context, ListaNotasActivity.class);
        context.startActivity(intent);
        context.finish();

        super.onPostExecute(aVoid);
    }
}
