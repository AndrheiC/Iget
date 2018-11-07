package br.com.smarttech.acs.iget.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;

import br.com.smarttech.acs.iget.helper.ConfiguracaoFirebase;

@Entity(tableName = "TB_Pessoa")
public class Pessoa {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "ID")
    private String id;
    private String nome;
    private String email;
    private int senha;
    private String urlImagem;
    private String nascimento;
    private String cartaoCredito;
    private String validadeCartao;
    private String cvv;

    public Pessoa() {
    }

    public Pessoa(String id, String nome, String email, int senha, String urlImagem, String nascimento, String cartaoCredito, String validadeCartao, String cvv) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.urlImagem = urlImagem;
        this.nascimento = nascimento;
        this.cartaoCredito = cartaoCredito;
        this.validadeCartao = validadeCartao;
        this.cvv = cvv;
    }

    public Pessoa(String id, String nome, String urlImagem, String nascimento, String cartaoCredito, String validadeCartao, String cvv) {
        this.id = id;
        this.nome = nome;
        this.urlImagem = urlImagem;
        this.nascimento = nascimento;
        this.cartaoCredito = cartaoCredito;
        this.validadeCartao = validadeCartao;
        this.cvv = cvv;
    }


    //Salvando os dados no Firebase...Mudar para room
    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getReferenciaFirebase();
        DatabaseReference pessoaRef = firebaseRef.child("pessoas").child(getId());
        pessoaRef.setValue(this);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(String cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public String getValidadeCartao() {
        return validadeCartao;
    }

    public void setValidadeCartao(String validadeCartao) {
        this.validadeCartao = validadeCartao;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}