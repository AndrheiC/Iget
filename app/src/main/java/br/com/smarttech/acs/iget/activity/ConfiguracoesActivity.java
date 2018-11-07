package br.com.smarttech.acs.iget.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.smarttech.acs.iget.DAO.PessoaDAO;
import br.com.smarttech.acs.iget.R;
import br.com.smarttech.acs.iget.helper.ConfiguracaoFirebase;
import br.com.smarttech.acs.iget.model.Pessoa;
import br.com.smarttech.acs.iget.repository.PessoaRepository;

public class ConfiguracoesActivity extends AppCompatActivity {

    private EditText editNomeUser, editNascimento, editCartaoCredito, editValidadeCartao, editCVV;
    private ImageView imagePerfil;
    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageReference;
    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;
    private String urlImagemSelecionada = "";
    Pessoa pessoa = new Pessoa();
    private PessoaRepository repository;
    Context context;


    ContextWrapper cw;
    File diretorio;
    //File diretorio = cw.getDir("igetImageDir", Context.MODE_PRIVATE);
    //String caminho = String.valueOf(diretorio);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        cw= new ContextWrapper(getApplicationContext());
        diretorio = cw.getDir("igetImageDir", Context.MODE_PRIVATE);
        String caminho = String.valueOf(diretorio);

        inicializarComponentes();
        storageReference = ConfiguracaoFirebase.getFirebaseStorage();
        idUsuarioLogado = ConfiguracaoFirebase.getIdUsuario();
        pessoa.setId(idUsuarioLogado);

        repository = new PessoaRepository(getApplicationContext());
        recuperarDadosUsuario(idUsuarioLogado);

        loadImageFromStorage(caminho, idUsuarioLogado);

        //Configura toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("IGet - Configurações");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imagePerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                if(i.resolveActivity(getPackageManager()) !=null){
                    startActivityForResult(i, SELECAO_GALERIA);

                }
            }
        });
    }

    //Recuperar dados - Inicio
    private void recuperarDadosUsuario(String id){

        pessoa = repository.pessoaPorId(id);

        editNomeUser.setText(pessoa.getNome().toString());
        editNascimento.setText(pessoa.getNascimento().toString());
        editCartaoCredito.setText(pessoa.getCartaoCredito().toString());
        editValidadeCartao.setText(pessoa.getValidadeCartao().toString());
        editCVV.setText(pessoa.getCvv().toString());
        //imagePerfil=loadImageFromStorage(caminho, idUsuarioLogado);

    }

    public void validarDadosUsuario(View view){

        //Valida se os campos foram preenchidos
        String nome = editNomeUser.getText().toString();
        String nascimento = editNascimento.getText().toString();
        String cartaoCredito = editCartaoCredito.getText().toString();
        String validadeCartao = editValidadeCartao.getText().toString();
        String cvv = editCVV.getText().toString();

        if(!nome.isEmpty()){
            if(!nascimento.isEmpty()){
                if(!cartaoCredito.isEmpty()){
                    if(!validadeCartao.isEmpty()){
                        if(!cvv.isEmpty()){


                            pessoa.setId(idUsuarioLogado);
                            pessoa.setNome(nome);
                            pessoa.setCartaoCredito(cartaoCredito);
                            pessoa.setNascimento(nascimento);
                            pessoa.setValidadeCartao(validadeCartao);

                            //Acho que o SQLite ou o ROOM não trabalham com date.
                            /*SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                Date dateNascimento = format.parse(nascimento);
                                Date dateValidadeCartao = format.parse(validadeCartao);
                                pessoa.setNascimento(dateNascimento);
                                pessoa.setValidadeCartao(dateValidadeCartao);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }*/

                            pessoa.setCvv(cvv);
                            pessoa.setUrlImagem(urlImagemSelecionada);
                            salvarPessoa(view);
                            //finish();
                        }else{
                            exibirMensagem("Digite o CVV do cartão de crédito!");
                        }

                    }else{
                        exibirMensagem("Digite a validade do cartão de crédito!");
                    }

                }else{
                    exibirMensagem("Digite o número do seu cartão de crédito!");
                }

            }else{
                exibirMensagem("Digite sua data de nascimento!");
            }

        }else{
            exibirMensagem("Digite seu nome completo!");
        }


    }

    public void exibirMensagem (String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    //Armazena imagem no firebase
    /*protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            Bitmap imagem = null;

            try{
                switch (requestCode){
                    case SELECAO_GALERIA:
                        Uri localImagem = data.getData();
                        imagem=MediaStore.Images.Media.getBitmap(getContentResolver(), localImagem);
                    break;
                }
                if(imagem!=null){
                    imagePerfil.setImageBitmap(imagem);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();


                    final StorageReference imagemRef = storageReference.child("imagens").child("user").child(idUsuarioLogado + "jpeg");
                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ConfiguracoesActivity.this, "Erro ao fazer upload da imagem", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            urlImagemSelecionada = imagemRef.getDownloadUrl().toString();
                            Toast.makeText(ConfiguracoesActivity.this, "Sucesso ao fazer upload da imagem", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    */

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
                    saveToInternalStorage(imagem, idUsuarioLogado);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    //Armazena imagem no app
    private String saveToInternalStorage(Bitmap bitmapImage, String idUsuario){

        //Colocado como global, mas deu nullPointerException
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        //path para /data/data/yourapp/app_data/igetImageDir
        File diretorio = cw.getDir("igetImageDir", Context.MODE_PRIVATE);
        //String caminho = String.valueOf(diretorio);


        // Cria igetImageDir
        File mypath=new File(diretorio,idUsuario + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Comprime para escrever no outPutStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            imagePerfil.setImageBitmap(bitmapImage);
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
    private void loadImageFromStorage(String path, String idUsuario)
    {
        try {
            File f=new File(path, idUsuario+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img=(ImageView)findViewById(R.id.imagePerfil);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public void salvarPessoa(View view){

        pessoa.setId(idUsuarioLogado);
        pessoa.setNome(editNomeUser.getText().toString());
        pessoa.setNascimento(editNascimento.getText().toString());
        pessoa.setCartaoCredito(editCartaoCredito.getText().toString());
        pessoa.setValidadeCartao(editValidadeCartao.getText().toString());
        pessoa.setCvv(editCVV.getText().toString());
        repository.update(pessoa);
        exibirMensagem("Dados salvos com sucesso");
    }


    private void inicializarComponentes(){
        imagePerfil = findViewById(R.id.imagePerfil);
        editNomeUser = findViewById(R.id.editTextNomeUser);
        editNascimento = findViewById(R.id.editTextNascimento);
        editCartaoCredito = findViewById(R.id.editTextCartaoCredito);
        editValidadeCartao = findViewById(R.id.editTextValidadeCC);
        editCVV = findViewById(R.id.editTextCVV);
    }
}
