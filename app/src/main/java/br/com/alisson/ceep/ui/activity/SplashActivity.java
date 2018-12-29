package br.com.alisson.ceep.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import br.com.alisson.ceep.R;
import br.com.alisson.ceep.preferences.SplashPreferences;
import br.com.alisson.ceep.ui.async.SplashAsync;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashAsync splashAsync = new SplashAsync(this);
        splashAsync.execute();
    }
}
