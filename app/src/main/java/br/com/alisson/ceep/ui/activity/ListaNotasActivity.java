package br.com.alisson.ceep.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.com.alisson.ceep.R;
import br.com.alisson.ceep.dao.NotaDAO;
import br.com.alisson.ceep.model.Nota;
import br.com.alisson.ceep.ui.recycler.adapter.ListaNotasAdapter;

import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.NOTA;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.REQUEST_CODE_FORMULARIO;
import static br.com.alisson.ceep.ui.activity.NotaInterfaceConstantes.RESULT_CODE;

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
        startActivityForResult(intent, REQUEST_CODE_FORMULARIO);
    }

    private List<Nota> pegaTodasNotas() {
        NotaDAO dao = new NotaDAO();
        return dao.todos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (isResultadoComNota(requestCode, resultCode, data)){
            Nota nota = (Nota) data.getSerializableExtra(NOTA);
            adicionaNota(nota);
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void adicionaNota(Nota nota) {
        new NotaDAO().insere(nota);
        adapter.adiciona(nota);
    }

    private boolean isResultadoComNota(int requestCode, int resultCode, @Nullable Intent data) {
        return isRequestCodeFormulario(requestCode) && isResultCodeFormulario(resultCode) && temNota(data);
    }

    private boolean temNota(@Nullable Intent data) {
        return data.hasExtra(NOTA);
    }

    private boolean isRequestCodeFormulario(int requestCode) {
        return requestCode == REQUEST_CODE_FORMULARIO;
    }

    private boolean isResultCodeFormulario(int resultCode) {
        return resultCode == RESULT_CODE;
    }

    private void configuraRecyclerView(List<Nota> notas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recycler_view);
        configuraAdapter(notas, listaNotas);
    }

    private void configuraAdapter(List<Nota> notas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, notas);
        listaNotas.setAdapter(adapter);
    }
}
