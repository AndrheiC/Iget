package br.com.smarttech.acs.iget.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.smarttech.acs.iget.R;
import br.com.smarttech.acs.iget.model.Produto;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.MyViewHolder> {

    private List<Produto> postagens;
    public ProdutoAdapter(List<Produto> produtoList) {
        this.postagens = produtoList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.postagem_detalhe,viewGroup,false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder( MyViewHolder myViewHolder, int i) {

        Produto produto = postagens.get(i);
        myViewHolder.nome.setText(produto.getNome());
        myViewHolder.descricao.setText(produto.getDescricao());
        myViewHolder.preco.setText(produto.getPreco());
        myViewHolder.imagem.setImageResource(produto.getImagem());

    }

    @Override
    public int getItemCount() {
        return postagens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nome;
        private TextView descricao;
        private TextView preco;
        private ImageView imagem;

        public MyViewHolder( View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textViewNome);
            descricao = itemView.findViewById(R.id.textViewPostagem);
            preco = itemView.findViewById(R.id.textViewPreco);
            imagem = itemView.findViewById(R.id.imageViewPostagem);
        }
    }
}
