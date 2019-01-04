package br.com.alisson.ceep.ui.recycler.helper.callback;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import br.com.alisson.ceep.dao.NotaDAO;
import br.com.alisson.ceep.model.Nota;
import br.com.alisson.ceep.ui.recycler.adapter.ListaNotasAdapter;

public class NotaItemTouchCallback extends ItemTouchHelper.Callback {
    private final ListaNotasAdapter adapter;
    private Context context;

    public NotaItemTouchCallback(ListaNotasAdapter adapter, Context context) {
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeslize = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int marcacoresArrastar = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(marcacoresArrastar, marcacoesDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = viewHolder1.getAdapterPosition();
        trocaNotas(posicaoInicial, posicaoFinal);
        return true;
    }

    private void trocaNotas(int posicaoInicial, int posicaoFinal) {
        Nota notaInicial = adapter.pegaNota(posicaoInicial);
        Nota notaFinal = adapter.pegaNota(posicaoFinal);

        new NotaDAO(context).troca(notaInicial, notaFinal);
        adapter.troca(posicaoInicial, posicaoFinal);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int posicao = viewHolder.getAdapterPosition();
        removeNotas(posicao);
    }

    private void removeNotas(int posicao) {
        Nota nota = adapter.pegaNota(posicao);
        new NotaDAO(context).remove(nota);
        adapter.remove(posicao);
    }
}
