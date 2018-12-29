package br.com.alisson.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import br.com.alisson.ceep.ui.recycler.adapter.listener.OnItemClickListener;

import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.NOTA;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.POSICAO;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity {

    public static final String TITLE_APP_CRIAR = "Insere notas";
    public static final String TITLE_APP_EDITAR = "Edita notas";

    static final String NOTA_TITULO = "NOTA_TITULO";
    static final String NOTA_DESCRICAO = "NOTA_DESCRICAO";
    static final String NOTA_COR_ID = "NOTA_COR_ID";
    static final String NOTA_COR_HEXA = "NOTA_COR_HEXA";

    private int posicao = POSICAO_INVALIDA;
    private EditText titulo;
    private EditText descricao;
    private SeletoresCoresAdapter adapter;
    private Cor cor = new CorDao().CorPadrao();
    private ConstraintLayout formularioCorpo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        inicializaCampos();

        setTitle(TITLE_APP_CRIAR);

        Intent intent = getIntent();
        if (intent.hasExtra(NOTA)) {
            setTitle(TITLE_APP_EDITAR);
            Nota nota = (Nota) intent.getSerializableExtra(NOTA);
            posicao = intent.getIntExtra(POSICAO, POSICAO_INVALIDA);
            preecheNota(nota);
        }


        configuraRecyclerView(new CorDao().listaCores());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        titulo.setText(savedInstanceState.getString(NOTA_TITULO));
        descricao.setText(savedInstanceState.getString(NOTA_DESCRICAO));
        int idCor = savedInstanceState.getInt(NOTA_COR_ID);
        String hexa = savedInstanceState.getString(NOTA_COR_HEXA);
        cor = new Cor(idCor, hexa);

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence(NOTA_TITULO, titulo.getText().toString());
        outState.putString(NOTA_DESCRICAO, descricao.getText().toString());
        outState.putInt(NOTA_COR_ID, cor.getIdCor());
        outState.putString(NOTA_COR_HEXA, cor.getCorHexa());

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
        setResult(Activity.RESULT_OK, intent);

    }

    @NonNull
    private Nota criaNota() {
        return new Nota(titulo.getText().toString(), descricao.getText().toString(), cor);
    }

    private void preecheNota(Nota nota) {
        titulo.setText(nota.getTitulo());
        descricao.setText(nota.getDescricao());
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
                FormularioNotaActivity.this.cor = cor;
                FormularioNotaActivity.this.formularioCorpo.setBackgroundColor(Color.parseColor(cor.getCorHexa()));
            }
        });
    }
}
