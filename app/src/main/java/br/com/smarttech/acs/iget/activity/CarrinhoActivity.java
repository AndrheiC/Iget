package br.com.smarttech.acs.iget.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.smarttech.acs.iget.R;
import br.com.smarttech.acs.iget.adapter.ProdutoAdapter;
import br.com.smarttech.acs.iget.model.Produto;
import br.com.smarttech.acs.iget.repository.ProdutoRepository;

public class CarrinhoActivity extends AppCompatActivity {
    RecyclerView recyclerViewCarrinho;
    private ProdutoAdapter adapterProduto;
    private List<Produto> produtos = new ArrayList<>();
    private Produto produto;
    private ProdutoRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        inicializarComponentes();
    }

    //Recuperar dados para a lista de produtos do recyclerView
    private void recuperarProdutosCarrinho() {
        produtos = repository.getAllProdutos();

        List<Produto> produtosList = repository.getAllProdutos();
        produtos.clear();
        for (Produto produto : produtosList) {
            String nome = produto.getNome();
            String descricao = produto.getDescricao();
            String preco = produto.getPreco();
            Produto postaProduto = new Produto(nome, descricao, preco, R.drawable.bolo);
            this.produtos.add(postaProduto);
            //adapterProduto.notifyDataSetChanged();
        }



    }

    private void inicializarComponentes() {
        recyclerViewCarrinho = findViewById(R.id.recyclerViewCarrinho);

    }

    public void exibirMensagem(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }
}
