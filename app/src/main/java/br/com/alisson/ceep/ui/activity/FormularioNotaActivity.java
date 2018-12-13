package br.com.alisson.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.alisson.ceep.R;
import br.com.alisson.ceep.model.Nota;

import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.NOTA;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.POSICAO;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.POSICAO_INVALIDA;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.RESULT_CODE_CRIAR;

public class FormularioNotaActivity extends AppCompatActivity {

    public static final String TITLE_APP_CRIAR = "Insere notas";
    public static final String TITLE_APP_EDITAR = "Edita notas";

    private int posicao = POSICAO_INVALIDA;
    private EditText titulo;
    private EditText descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);


        inicializaCampos();

        setTitle(TITLE_APP_CRIAR);
        Intent intent = getIntent();
        if (intent.hasExtra(NOTA)){
            setTitle(TITLE_APP_EDITAR);
            Nota nota = (Nota) intent.getSerializableExtra(NOTA);
            posicao = intent.getIntExtra(POSICAO, POSICAO_INVALIDA);
            preecheNota(nota);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(isMenuSalvaNota(item)){
            Nota nota = criaNota();
            retornaNota(nota);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Nota nota) {
        Intent intent = new Intent();
        intent.putExtra(NOTA, nota);
        intent.putExtra(POSICAO, posicao);
        setResult(RESULT_CODE_CRIAR, intent);
    }

    @NonNull
    private Nota criaNota() {
        return new Nota(titulo.getText().toString(), descricao.getText().toString());
    }

    private void preecheNota(Nota nota) {
        titulo.setText(nota.getTitulo());
        descricao.setText(nota.getDescricao());
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
    }

    private boolean isMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_ic_salva;
    }
}
