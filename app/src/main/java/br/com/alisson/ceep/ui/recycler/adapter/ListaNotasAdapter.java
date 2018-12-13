package br.com.alisson.ceep.ui.recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.alisson.ceep.R;
import br.com.alisson.ceep.model.Nota;
import br.com.alisson.ceep.ui.recycler.adapter.listener.OnItemClickListener;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private List<Nota> notas;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ListaNotasAdapter(Context context, List<Nota> notas) {
        this.notas = notas;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_nota, viewGroup, false);

        return new NotaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder notaViewHolder, int position) {
        Nota nota = notas.get(position);
        notaViewHolder.vincula(nota);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public void adiciona(Nota nota){
        this.notas.add(nota);
        notifyDataSetChanged();
    }

    public void altera(int posicao, Nota nota) {
        notas.set(posicao, nota);
        notifyDataSetChanged();
    }

    class NotaViewHolder extends RecyclerView.ViewHolder{

        private final TextView titulo;
        private final TextView descricao;
        private Nota nota;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(nota, getAdapterPosition());
                }
            });
        }

        public void vincula(Nota nota){
            this.nota = nota;
            preencheCampo(nota);
        }

        private void preencheCampo(Nota nota) {
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
        }
    }

}
