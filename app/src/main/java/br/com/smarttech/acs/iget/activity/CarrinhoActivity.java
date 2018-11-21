package br.com.smarttech.acs.iget.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.smarttech.acs.iget.DAO.CompraDAO;
import br.com.smarttech.acs.iget.R;
import br.com.smarttech.acs.iget.adapter.CarrinhoAdapter;
import br.com.smarttech.acs.iget.helper.ConfiguracaoFirebase;
import br.com.smarttech.acs.iget.model.Compra;
import br.com.smarttech.acs.iget.model.Produto;
import br.com.smarttech.acs.iget.repository.Repository;

public class CarrinhoActivity extends AppCompatActivity {
    RecyclerView recyclerViewCarrinho;
    private CarrinhoAdapter adapterProduto;
    private List<Compra> produtos;
    private Compra produto;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        repository = new Repository(getApplicationContext());
        produtos = new ArrayList<>();
        produto = new Compra();


        inicializarComponentes();
        recuperarProdutosCarrinho();

        adapterProduto = new CarrinhoAdapter(produtos, this);
        recyclerViewCarrinho.setAdapter(adapterProduto);

        //Configurar recyclerView
        recyclerViewCarrinho.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCarrinho.setHasFixedSize(true);


        //Configura toolbar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("IGet - Carrinho de compras");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    //Recuperar dados para a lista de produtos do recyclerView
    private void recuperarProdutosCarrinho() {
        String idUsuarioLogado = ConfiguracaoFirebase.getIdUsuario();
        produtos = repository.getCompraRepository().getAllCompra(idUsuarioLogado);

        List<Compra> produtosList = repository.getCompraRepository().getAllCompra(idUsuarioLogado);
        produtos.clear();
        for (Compra produto : produtosList) {
            int idCompra = produto.getId();
            String valor = produto.getValor();
            String dataCompra = produto.getDataCompra();
            String dataColeta = produto.getDataColeta();
            String horaColeta = produto.getHoraColeta();
            int qtd = produto.getQtd();
            String idPessoa = produto.getIdPessoa();
            List<Produto> listaProduto = produto.getProdutoList();
            Compra postaProduto = new Compra(valor, dataCompra, dataColeta, horaColeta, qtd, idPessoa, listaProduto);
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
