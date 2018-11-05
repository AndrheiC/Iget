package br.com.smarttech.acs.iget.activity;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.smarttech.acs.iget.R;
import br.com.smarttech.acs.iget.helper.ConfiguracaoFirebase;
import br.com.smarttech.acs.iget.model.Pessoa;

public class ConfiguracoesActivity extends AppCompatActivity {

    private EditText editNomeUser, editNascimento, editCartaoCredito, editValidadeCartao, editCVV;
    private ImageView imagePerfil;
    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageReference;
    private String idUsuarioLogado;
    private String urlImagemSelecionada = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        inicializarComponentes();
        storageReference = ConfiguracaoFirebase.getFirebaseStorage();
        idUsuarioLogado = ConfiguracaoFirebase.getIdUsuario();

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

                            Pessoa pessoa = new Pessoa();
                            pessoa.setId(idUsuarioLogado);
                            pessoa.setNome(nome);
                            pessoa.setCartaoCredito(cartaoCredito);

                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                Date dateNascimento = format.parse(nascimento);
                                Date dateValidadeCartao = format.parse(validadeCartao);
                                pessoa.setNascimento(dateNascimento);
                                pessoa.setValidadeCartao(dateValidadeCartao);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            pessoa.setCvv(cvv);
                            pessoa.setUrlImagem(urlImagemSelecionada);
                            pessoa.salvar();
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

    //@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

    private void inicializarComponentes(){
        imagePerfil = findViewById(R.id.imagePerfil);
        editNomeUser = findViewById(R.id.editTextNomeUser);
        editNascimento = findViewById(R.id.editTextNascimento);
        editCartaoCredito = findViewById(R.id.editTextCartaoCredito);
        editValidadeCartao = findViewById(R.id.editTextValidadeCC);
        editCVV = findViewById(R.id.editTextCVV);
    }
}
