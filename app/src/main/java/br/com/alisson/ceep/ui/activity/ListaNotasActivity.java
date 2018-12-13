package br.com.alisson.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.com.alisson.ceep.R;
import br.com.alisson.ceep.dao.NotaDAO;
import br.com.alisson.ceep.model.Nota;
import br.com.alisson.ceep.ui.recycler.adapter.ListaNotasAdapter;
import br.com.alisson.ceep.ui.recycler.adapter.listener.OnItemClickListener;

import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.NOTA;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.POSICAO;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.REQUEST_CODE_FORMULARIO_CRIAR;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.REQUEST_CODE_FORMULARIO_EDITAR;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.RESULT_CODE_CRIAR;

public class ListaNotasActivity extends AppCompatActivity {

    public static final String TITLE_APP = "Notas";
    private ListaNotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        setTitle(TITLE_APP);

        List<Nota> notas = pegaTodasNotas();
        configuraRecyclerView(notas);

        configuraBotaoInsereNota();
    }

    private void configuraBotaoInsereNota() {
        TextView insereNota = findViewById(R.id.lista_notas_insere_nota);

        insereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiParaFormulario();
            }
        });
    }

    private void vaiParaFormulario() {
        Intent intent = new Intent(this, FormularioNotaActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FORMULARIO_CRIAR);
    }

    private List<Nota> pegaTodasNotas() {
        NotaDAO dao = new NotaDAO();

        for (int i = 1; i < 11;i++){
            dao.insere(new Nota("Titulo "+ i, "Descricao "+ i));
        }

        return dao.todos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (isResultadoComNotaCriar(requestCode, resultCode, data)){
            Nota nota = (Nota) data.getSerializableExtra(NOTA);
            adicionaNota(nota);
        }

        if (isResultadoComNotaEditar(requestCode, resultCode, data)){
            Nota nota = (Nota) data.getSerializableExtra(NOTA);
            int posicao = data.getIntExtra(POSICAO, -1);
            new NotaDAO().altera(posicao, nota);
            adapter.altera(posicao, nota);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void adicionaNota(Nota nota) {
        new NotaDAO().insere(nota);
        adapter.adiciona(nota);
    }

    private boolean isResultadoComNotaCriar(int requestCode, int resultCode, @Nullable Intent data) {
        return isRequestCodeFormularioCriar(requestCode) && isResultCodeFormulario(resultCode) && temNota(data);
    }

    private boolean isResultadoComNotaEditar(int requestCode, int resultCode, @Nullable Intent data) {
        return isRequestCodeFormularioEditar(requestCode) && isResultCodeFormulario(resultCode) && temNota(data) && temPosicao(data);
    }

    private boolean temNota(@Nullable Intent data) {
        return data.hasExtra(NOTA);
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
        return resultCode == RESULT_CODE_CRIAR;
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
                Intent intent = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
                intent.putExtra(NOTA, nota);
                intent.putExtra(POSICAO, posicao);
                startActivityForResult(intent, REQUEST_CODE_FORMULARIO_EDITAR);
            }
        });
    }
}
