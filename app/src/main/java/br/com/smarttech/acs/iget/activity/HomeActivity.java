package br.com.smarttech.acs.iget.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import br.com.smarttech.acs.iget.R;
import br.com.smarttech.acs.iget.adapter.ProdutoAdapter;
import br.com.smarttech.acs.iget.helper.ConfiguracaoFirebase;
import br.com.smarttech.acs.iget.model.Produto;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerPostagem;
    private List<Produto> postagens = new ArrayList<>();

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //vincula com o objeto criado no xml
        recyclerPostagem = findViewById(R.id.recyclerViewPostagem);

        //inicia firebase
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //Define o layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerPostagem.setLayoutManager(layoutManager);

        //Define o adapter
        this.prepararPostagens();
        ProdutoAdapter adapter = new ProdutoAdapter(postagens);
        recyclerPostagem.setAdapter(adapter);

        //Configura toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("IGet - Compras");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_compras, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menuSair:
                deslogarUsuario();
                break;

            case R.id.menuAlterarDados:
                alterarDados();
                break;
            case R.id.menuExcluirDados:
                excluirUsuario();
                break;
            case R.id.menuCompras:
                carrinhoCompras();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario(){
        try{
            autenticacao.signOut();
            //startActivity(new Intent(HomeActivity.this, AutenticacaoActivity.class));
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void alterarDados(){
        startActivity(new Intent(HomeActivity.this, ConfiguracoesActivity.class ));

    }

    private void carrinhoCompras(){
        startActivity(new Intent(HomeActivity.this, ComprasActivity.class ));

    }

    private void excluirUsuario(){
        autenticacao.getCurrentUser().delete();
        startActivity(new Intent(HomeActivity.this, AutenticacaoActivity.class));
    }

    public void prepararPostagens(){

        Produto produto = new Produto("Pão Francês (2 unidades)", "Pão Francês quentinho", "R$2,50", R.drawable.paofrances);
        this.postagens.add(produto);

        produto = new Produto("Café com Leite (Large)", "Café com leite grande, cremoso, com pitada de canela em pó e chantily", "R$3,50", R.drawable.cafe);
        this.postagens.add(produto);

        produto = new Produto("Pão de Queijo (3 unidades)", "Pão de queijo mineirinho, recém saído do forno", "R$3,00", R.drawable.paoqueijo);
        this.postagens.add(produto);

        produto = new Produto("Sonho", "Delicioso sonho frito. Sabores: Creme, nata, doce-de-leite, goiabada", "R$5,50", R.drawable.sonho);
        this.postagens.add(produto);

        produto = new Produto("Torta de maçã (fatia)", "Fatia de torta de maçã (150g)", "R$5,50", R.drawable.torta);
        this.postagens.add(produto);

        produto = new Produto("Bolo de Morango com Chocolate (fatia)", "Delicioso bolo de morango com chocolate, molhadinho (200g)", "R$7,00", R.drawable.bolo);
        this.postagens.add(produto);
    }

    public void openDialog(View view){

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Confirma compra?");
        dialog.setMessage("Ao clicar em SIM você estará confirmando a compra");

        dialog.setCancelable(false);
        dialog.setIcon(android.R.drawable.ic_input_add);


        dialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Fazer a ação de comprar aqui.
            }
        });

        dialog.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Fazer a ação de cancelamento aqui.
            }
        });

        dialog.create();
        dialog.show();
    }

    public void cancel (View view){

    }
}
