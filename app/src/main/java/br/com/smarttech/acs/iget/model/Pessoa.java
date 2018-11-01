package br.com.smarttech.acs.iget.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "TB_Pessoa")
public class Pessoa {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;
    private String nome;
    private String email;
    private int senha;

    public Pessoa() {
    }

    public Pessoa(int id, String nome, String email, int senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
