package br.com.smarttech.acs.iget.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.smarttech.acs.iget.R;
import br.com.smarttech.acs.iget.adapter.ProdutoAdapter;
import br.com.smarttech.acs.iget.listener.RecyclerItemClickListener;
import br.com.smarttech.acs.iget.model.Produto;
import br.com.smarttech.acs.iget.repository.ProdutoRepository;

public class CadastrarProdutoActivity extends AppCompatActivity {
    private EditText editNomeProduto, editDescricao, editPrecoProduto, editIdPesquisa;
    private ImageView imageProduto;
    private RecyclerView recyclerViewProduto;
    private ProdutoAdapter adapterProduto;
    private List<Produto> produtos = new ArrayList<>();
    private Produto produto;
    private int idProduto;
    private static final int SELECAO_GALERIA = 200;
    ContextWrapper cw;
    File diretorio;
    private ProdutoRepository repository;
    int idPesquisa;
    public String caminho;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);

        //Salvar e carregar imagem
        salvarCarregarImagem();

        inicializarComponentes();
        repository = new ProdutoRepository(getApplicationContext());
        produto = new Produto();
        idProduto = produto.getId();

        //Configura toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("IGet - Cadastrar produto");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Configurar recyclerView
        recyclerViewProduto.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProduto.setHasFixedSize(true);

        recuperarProdutos();

        //Adiciona evento de clique
        recyclerViewProduto.addOnItemTouchListener( new RecyclerItemClickListener(this, recyclerViewProduto, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {

                Produto produtoSelecionado = produtos.get(position);
                String nome = produtoSelecionado.getNome();
                repository.delete(nome);
                Toast.makeText(getApplicationContext(),"Deletado!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CadastrarProdutoActivity.this, HomeActivity.class));
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        adapterProduto = new ProdutoAdapter(produtos, this);
        recyclerViewProduto.setAdapter(adapterProduto);


        imageProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(i, SELECAO_GALERIA);

                }
            }
        });
    }

    public void salvarCarregarImagem() {
        cw = new ContextWrapper(getApplicationContext());
        diretorio = cw.getDir("igetImageDir", Context.MODE_PRIVATE);
        caminho = String.valueOf(diretorio);
    }

    public void pesquisaProduto(View view) {
        exibirMensagem("pesquisado");
        recuperarDadosProduto(idPesquisa);
    }


    //Recuperar dados - Inicio
    private void recuperarDadosProduto(int id) {

        produto = repository.produtoPorId(id);

        if (produto != null) {
            editNomeProduto.setText(produto.getNome().toString());
            editDescricao.setText(produto.getDescricao().toString());
            editPrecoProduto.setText(produto.getPreco().toString());
            loadImageFromStorage(caminho, idPesquisa);
        } else {
            exibirMensagem("null");
        }

    }

    //Recuperar dados para a lista de produtos do recyclerView
    private void recuperarProdutos() {
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


    public void validarDadosProduto(View view) {

        //Valida se os campos foram preenchidos
        String nome = editNomeProduto.getText().toString();
        String descricao = editDescricao.getText().toString();
        String precoProduto = editPrecoProduto.getText().toString();

        if (!nome.isEmpty()) {
            if (!descricao.isEmpty()) {
                if (!precoProduto.isEmpty()) {
                    produto = new Produto();
                    produto.setNome(nome);
                    produto.setPreco(precoProduto);

                    if (repository.produtoPorId(idProduto) != null) {
                        salvarUpdateProduto(view);
                    } else {
                        salvarInsertProduto(view);
                    }
                    //finish();


                } else {
                    exibirMensagem("Digite o valor do produto");
                }

            } else {
                exibirMensagem("Digite uma descrição suscinta!");
            }

        } else {
            exibirMensagem("Digite o nome do produto!");
        }


    }

    public void salvarUpdateProduto(View view) {

        produto.setNome(editNomeProduto.getText().toString());
        produto.setDescricao(editDescricao.getText().toString());
        produto.setPreco(editPrecoProduto.getText().toString());
        repository.update(produto);
        exibirMensagem("Dados salvos com sucesso");
        startActivity(new Intent(CadastrarProdutoActivity.this, HomeActivity.class));
    }

    public void salvarInsertProduto(View view) {

        produto.setNome(editNomeProduto.getText().toString());
        produto.setDescricao(editDescricao.getText().toString());
        produto.setPreco(editPrecoProduto.getText().toString());
        repository.insert(produto);
        exibirMensagem("Dados salvos com sucesso");
        startActivity(new Intent(CadastrarProdutoActivity.this, HomeActivity.class));
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap imagem = null;

            try {
                switch (requestCode) {
                    case SELECAO_GALERIA:
                        Uri localImagem = data.getData();
                        try {
                            imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagem);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                }
                if (imagem != null) {
                    //imagePerfil.setImageBitmap(imagem);
                    saveToInternalStorage(imagem, idProduto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //Armazena imagem no app
    private String saveToInternalStorage(Bitmap bitmapImage, int idProduto) {

        //Colocado como global, mas deu nullPointerException
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        //path para /data/data/yourapp/app_data/igetImageDir
        File diretorio = cw.getDir("igetImageDir", Context.MODE_PRIVATE);
        //String caminho = String.valueOf(diretorio);

        Bitmap bitmapImageBd = bitmapImage;


        // Cria igetImageDir
        File mypath = new File(diretorio, idProduto + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Comprime para escrever no outPutStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            imageProduto.setImageBitmap(bitmapImage);
            exibirMensagem("Imagem salva!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return diretorio.getAbsolutePath();
    }

    //Tentativa de ler imagem do app
    public void loadImageFromStorage(String path, int idProduto) {
        try {
            File f = new File(path, idProduto + ".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img = (ImageView) findViewById(R.id.imageProduto);
            img.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void exibirMensagem(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void inicializarComponentes() {
        imageProduto = findViewById(R.id.imageProduto);
        editNomeProduto = findViewById(R.id.editTextNomeProduto);
        editDescricao = findViewById(R.id.editTextDescricao);
        editPrecoProduto = findViewById(R.id.editTextPrecoProduto);
        recyclerViewProduto = findViewById(R.id.recyclerViewProdutos);

    }
}
