package br.com.alisson.ceep.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import br.com.alisson.ceep.R;

public class FormularioFeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_feedback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_feedback, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (ehEnviaFeedback(item)){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean ehEnviaFeedback(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_envia_feedback;
    }
}
