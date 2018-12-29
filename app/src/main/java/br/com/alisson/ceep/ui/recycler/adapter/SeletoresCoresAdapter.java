package br.com.alisson.ceep.ui.recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import br.com.alisson.ceep.R;
import br.com.alisson.ceep.model.Nota;
import br.com.alisson.ceep.ui.recycler.adapter.listener.OnItemClickListener;

public class SeletoresCoresAdapter extends RecyclerView.Adapter<SeletoresCoresAdapter.SeletorViewHolder> {

    private CorClickListener corClickListener;
    private List<String> cores;
    private Context context;

    public SeletoresCoresAdapter(List<String> cores, Context context) {
        this.cores = cores;
        this.context = context;
    }

    @NonNull
    @Override
    public SeletorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View seletor = LayoutInflater.from(context).inflate(R.layout.item_seletor_cor, viewGroup, false);

        return new SeletorViewHolder(seletor);
    }

    @Override
    public void onBindViewHolder(@NonNull SeletorViewHolder seletorViewHolder, int i) {
        String cor = cores.get(i);
        seletorViewHolder.vincula(cor);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setOnCorClickListener(CorClickListener corClickListener) {
        this.corClickListener = corClickListener;
    }

    class SeletorViewHolder extends RecyclerView.ViewHolder{

        private final FrameLayout corView;
        private String cor;

        public SeletorViewHolder(@NonNull View itemView) {
            super(itemView);
            corView = itemView.findViewById(R.id.item_seletor_cor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    corClickListener.onItemClick();
                }
            });
        }

        public void vincula(String cor){
            this.cor = cor;
            preencheCampo(cor);
        }

        private void preencheCampo(String cor) {
            corView.setBackground(context.getResources().getDrawable(R.drawable.amarelo));
        }
    }

    interface CorClickListener{
        public void onItemClick();
    }
}
