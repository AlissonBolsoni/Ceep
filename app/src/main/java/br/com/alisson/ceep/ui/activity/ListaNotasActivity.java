package br.com.alisson.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.alisson.ceep.R;
import br.com.alisson.ceep.dao.NotaDAO;
import br.com.alisson.ceep.model.Nota;
import br.com.alisson.ceep.preferences.ListaNotasPreferences;
import br.com.alisson.ceep.ui.recycler.adapter.ListaNotasAdapter;
import br.com.alisson.ceep.ui.recycler.adapter.listener.OnItemClickListener;
import br.com.alisson.ceep.ui.recycler.helper.callback.NotaItemTouchCallback;

import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.NOTA;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.POSICAO;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.POSICAO_INVALIDA;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.REQUEST_CODE_FORMULARIO_CRIAR;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.REQUEST_CODE_FORMULARIO_EDITAR;

public class ListaNotasActivity extends AppCompatActivity {

    private static final String TITLE_APP = "Notas";
    private ListaNotasAdapter adapter;
    private RecyclerView listaNotasRecyclerView;
    private ListaNotasPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        setTitle(TITLE_APP);

        listaNotasRecyclerView = findViewById(R.id.lista_notas_recycler_view);

        List<Nota> notas = pegaTodasNotas();
        configuraRecyclerView(notas);

        configuraBotaoInsereNota();
    }

    @Override
    protected void onResume() {
        super.onResume();

        preferences = new ListaNotasPreferences(this);
    }

    private void configuraBotaoInsereNota() {
        TextView insereNota = findViewById(R.id.lista_notas_insere_nota);

        insereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiParaFormularioCriar();
            }
        });
    }

    private void vaiParaFormularioCriar() {
        Intent intent = new Intent(this, FormularioNotaActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FORMULARIO_CRIAR);
    }

    private void vaiParaFormularioAlterar(int posicao, Nota nota){
        Intent intent = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        intent.putExtra(NOTA, nota);
        intent.putExtra(POSICAO, posicao);
        startActivityForResult(intent, REQUEST_CODE_FORMULARIO_EDITAR);
    }

    private List<Nota> pegaTodasNotas() {
        NotaDAO dao = new NotaDAO(this);
        return dao.todos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (isResultadoComNotaCriar(requestCode, resultCode, data)){            
            if(isResultCodeFormulario(resultCode)){
                Nota nota = (Nota) data.getSerializableExtra(NOTA);
                adicionaNota(nota);
            }
        }

        if (isResultadoComNotaEditar(requestCode, resultCode, data)){
            if(isResultCodeFormulario(resultCode)){
                Nota nota = (Nota) data.getSerializableExtra(NOTA);
                int posicao = data.getIntExtra(POSICAO, POSICAO_INVALIDA);
                if(isPosicaoValida(posicao)){
                    alteraNota(posicao, nota);
                }else{
                    Toast.makeText(this, "ERRO!!!. Não foi possível alterar a nota.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isPosicaoValida(int posicao){
        return posicao > POSICAO_INVALIDA;
    }

    private void alteraNota(int posicao, Nota nota) {
        new NotaDAO(this).altera(nota);
        adapter.altera(posicao, nota);
    }

    private void adicionaNota(Nota nota) {
        new NotaDAO(this).insere(nota);
        adapter.adiciona(nota);
    }

    private boolean isResultadoComNotaCriar(int requestCode, int resultCode, @Nullable Intent data) {
        return isRequestCodeFormularioCriar(requestCode) && temNota(data);
    }

    private boolean isResultadoComNotaEditar(int requestCode, int resultCode, @Nullable Intent data) {
        return isRequestCodeFormularioEditar(requestCode) && temNota(data);
    }

    private boolean temNota(@Nullable Intent data) {
        return data != null && data.hasExtra(NOTA);
    }

    private boolean temPosicao(@Nullable Intent data) {
        return data.hasExtra(POSICAO);
    }

    private boolean isRequestCodeFormularioCriar(int requestCode) {
        return requestCode == REQUEST_CODE_FORMULARIO_CRIAR;
    }

    private boolean isRequestCodeFormularioEditar(int requestCode) {
        return requestCode == REQUEST_CODE_FORMULARIO_EDITAR;
    }

    private boolean isResultCodeFormulario(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private void configuraRecyclerView(List<Nota> notas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recycler_view);
        configuraAdapter(notas, listaNotas);
    }

    private void configuraAdapter(List<Nota> notas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, notas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int posicao) {
                vaiParaFormularioAlterar(posicao, nota);
            }
        });

        configuraItemTouchHelper(listaNotas);
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchCallback(adapter, this));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_notas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_lista_troca_layout);
        selecionaLayout(item, false);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(isMenuTrocaLayout(item)){
            selecionaLayout(item, true);
        }else if (ehMenuVaiParaFeedback(item)){
            Intent intent = new Intent(this, FormularioFeedbackActivity.class);
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }

    private boolean ehMenuVaiParaFeedback(MenuItem item) {
        return item.getItemId() == R.id.menu_lista_vai_para_feedback;
    }

    private void selecionaLayout(MenuItem item, Boolean click) {
        String tipo = preferences.getTipoLayout();

        if (click){
            tipo = TipoListaLayout.trocaLayout(tipo, this);
        }


        if (TipoListaLayout.ehGrid(tipo)){
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            listaNotasRecyclerView.setLayoutManager(layoutManager);
            item.setIcon(R.drawable.ic_action_linear_layout);
        }else if (TipoListaLayout.ehLinear(tipo)){
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            listaNotasRecyclerView.setLayoutManager(layoutManager);
            item.setIcon(R.drawable.ic_action_grid_layout);
        }
    }

    private boolean isMenuTrocaLayout(MenuItem item){
        return item.getItemId() == R.id.menu_lista_troca_layout;
    }
}
