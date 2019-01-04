package br.com.alisson.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.List;

import br.com.alisson.ceep.R;
import br.com.alisson.ceep.dao.CorDao;
import br.com.alisson.ceep.model.Cor;
import br.com.alisson.ceep.model.Nota;
import br.com.alisson.ceep.ui.recycler.adapter.SeletoresCoresAdapter;

import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.NOTA;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.POSICAO;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity {

    private static final String NOTA_SAVE = "NOTA_SAVE";
    private static final String TITLE_APP_CRIAR = "Insere notas";
    private static final String TITLE_APP_EDITAR = "Edita notas";

    private int posicao = POSICAO_INVALIDA;
    private EditText titulo;
    private EditText descricao;
    private SeletoresCoresAdapter adapter;
    private ConstraintLayout formularioCorpo;
    private Nota nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        inicializaCampos();
        setTitle(TITLE_APP_CRIAR);

        Intent intent = getIntent();
        if (temNota(intent)) {
            setTitle(TITLE_APP_EDITAR);
            nota = (Nota) intent.getSerializableExtra(NOTA);
            posicao = intent.getIntExtra(POSICAO, POSICAO_INVALIDA);
            preecheNota();
        } else {
            this.nota = criaNota();
        }

        configuraRecyclerView(new CorDao().listaCores());
    }

    private boolean temNota(Intent intent) {
        return intent != null && intent.hasExtra(NOTA);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        this.nota = (Nota) savedInstanceState.getSerializable(NOTA_SAVE);

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(NOTA_SAVE, nota);

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isMenuSalvaNota(item)) {
            salvaNota();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Nota nota) {
        Intent intent = new Intent();
        intent.putExtra(NOTA, nota);
        intent.putExtra(POSICAO, posicao);
        setResult(Activity.RESULT_OK, intent);
    }

    @NonNull
    private Nota criaNota() {
        return new Nota(titulo.getText().toString(), descricao.getText().toString(), new CorDao().CorPadrao());
    }

    @NonNull
    private void salvaNota() {
        this.nota = new Nota(nota.getId(), titulo.getText().toString(), descricao.getText().toString(), nota.getCor(), nota.getPosicao(), nota.getDesativado());
        retornaNota(nota);
    }

    private void preecheNota() {
        titulo.setText(nota.getTitulo());
        descricao.setText(nota.getDescricao());
        formularioCorpo.setBackgroundColor(Color.parseColor(nota.getCor().getCorHexa()));
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
        formularioCorpo = findViewById(R.id.formulario_corpo);
    }

    private boolean isMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_ic_salva;
    }

    private void configuraRecyclerView(List<Cor> cores) {
        RecyclerView listaCores = findViewById(R.id.formulario_nota_seletores);
        configuraAdapter(cores, listaCores);
    }

    private void configuraAdapter(List<Cor> cores, RecyclerView listaCores) {
        adapter = new SeletoresCoresAdapter(cores, this);
        listaCores.setAdapter(adapter);
        adapter.setOnCorClickListener(new SeletoresCoresAdapter.CorClickListener() {

            @Override
            public void onItemClick(Cor cor) {
                FormularioNotaActivity.this.nota.setCor(cor);
                FormularioNotaActivity.this.formularioCorpo.setBackgroundColor(Color.parseColor(cor.getCorHexa()));
            }
        });
    }
}
