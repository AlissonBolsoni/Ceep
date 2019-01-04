package br.com.alisson.ceep.ui.async;

import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

public class SplashAsync extends AsyncTask<Boolean, Void, Void> {


    private SplashCallback callback;

    public SplashAsync(SplashCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Boolean... params) {

        boolean jaConectado = false;
        if (params.length > 0)
            jaConectado = params[0];

        try {
            if (jaConectado) {
                Thread.sleep(500);
            } else {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        callback.splashCallback();
        super.onPostExecute(aVoid);
    }

    public interface SplashCallback {
        void splashCallback();
    }
}
