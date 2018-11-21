package br.com.smarttech.acs.iget.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.smarttech.acs.iget.R;
import br.com.smarttech.acs.iget.model.Compra;
import br.com.smarttech.acs.iget.model.Produto;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.MyViewHolder> {
    private List<Compra> produtos;
    private Context context;

    public CarrinhoAdapter(List<Compra> produtos, Context context) {
        this.produtos = produtos;
        this.context = context;
    }

    @NonNull
    @Override
    public CarrinhoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_carrinho, parent, false);
        return new CarrinhoAdapter.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrinhoAdapter.MyViewHolder holder, int i) {
        Compra produto = produtos.get(i);
        holder.dtCompra.setText(produto.getDataCompra());

        List<Produto> listaProduto = produto.getProdutoList();
        int idProdutoComprado = 0;
        String nomeProduto = "";
        String descricaoProduto = "";
        for (Produto produto1 : listaProduto) {
            nomeProduto = produto1.getNome();
            descricaoProduto = produto1.getDescricao();
            idProdutoComprado = produto1.getId();
        }
        holder.produtoComprado.setText(nomeProduto);
        holder.descricaoProdutoComprado.setText(descricaoProduto);

        //holder.qtd.setText(produto.getQtd());
        holder.preco.setText(produto.getValor());
        holder.dtCompra.setText(produto.getDataCompra());
        holder.dtRetirada.setText(produto.getDataColeta());
        holder.horaRetirada.setText(produto.getHoraColeta());

    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dtCompra;
        TextView produtoComprado;
        TextView descricaoProdutoComprado;
        TextView qtd;
        TextView preco;
        TextView dtRetirada;
        TextView horaRetirada;

        public MyViewHolder(View itemView) {
            super(itemView);

            dtCompra = itemView.findViewById(R.id.textDataCompra);
            produtoComprado = itemView.findViewById(R.id.textNomeRefeicao);
            descricaoProdutoComprado = itemView.findViewById(R.id.textDescricaoRefeicao);
            //qtd = itemView.findViewById(R.id.textViewQtd);

            //
            preco = itemView.findViewById(R.id.textPreco);
            dtRetirada = itemView.findViewById(R.id.textDataRetirada);
            horaRetirada = itemView.findViewById(R.id.textHoraRetirada);
        }
    }
}
