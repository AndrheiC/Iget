package br.com.smarttech.acs.iget.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import br.com.smarttech.acs.iget.R;
import br.com.smarttech.acs.iget.helper.ConfiguracaoFirebase;


public class AutenticacaoActivity extends AppCompatActivity {
    private Button botaoAcessar;
    private Switch tipoAcesso;
    private EditText campoEmail, campoSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);


        inicializaComponentes();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //autenticacao.signOut();

        verificarUsuarioLogado();

        botaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                //Verifica se campos foram preenchidos
                if(!email.isEmpty()){
                    if(!senha.isEmpty()){
                        //Verificar estado do switch
                        if(tipoAcesso.isChecked()) { //Cadastro

                            autenticacao.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()) {

                                        Toast.makeText(AutenticacaoActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                        abrirTelaPrincipal();

                                    } else {

                                        String erroExcecao ="";

                                        try{
                                            throw task.getException();
                                        }catch (FirebaseAuthWeakPasswordException e){
                                            erroExcecao = "Digite uma senha mais forte!";
                                        } catch (FirebaseAuthInvalidCredentialsException e){
                                            erroExcecao = "Digite um e-mail válido!";
                                        }catch (FirebaseAuthUserCollisionException e){
                                            erroExcecao = "Este e-mail já está em uso por um usuário!";
                                        }catch (Exception e) {
                                            erroExcecao = "ao cadastrar usuário: " + e.getMessage();
                                            e.printStackTrace();
                                        }

                                        //Mensagem de erro, de acordo com o tratamento
                                        Toast.makeText(AutenticacaoActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();


                                    }
                                }
                            });

                        }else { //Logar
                            autenticacao.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AutenticacaoActivity.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                                        abrirTelaPrincipal();
                                    }else{
                                        Toast.makeText(AutenticacaoActivity.this, "Erro ao efetuar login", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }

                    }else{
                        Toast.makeText(AutenticacaoActivity.this,"Preencha a Senha", Toast.LENGTH_LONG).show();

                    }
                }else{
                    Toast.makeText(AutenticacaoActivity.this,"Preencha o E-mail", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void abrirTelaPrincipal(){
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

    }

    private void verificarUsuarioLogado(){

        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        if(usuarioAtual!= null){
            abrirTelaPrincipal();
        }
    }

    private void inicializaComponentes(){
        campoEmail = findViewById(R.id.editTextUsuario);
        campoSenha = findViewById(R.id.editTextSenha);
        botaoAcessar = findViewById(R.id.buttonAcesso);
        tipoAcesso =findViewById(R.id.switchLoginCadastro);
    }
}
