package br.com.smarttech.acs.iget.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import br.com.smarttech.acs.iget.repository.ProdutoRepository;
import br.com.smarttech.acs.iget.repository.Repository;

@Entity(tableName = "TB_Produto")
public class Produto {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;
    private String nome;
    private String descricao;
    private String preco;
    private int imagem;

    public Produto() {
    }

    public Produto(String nome, String descricao, String preco, int imagem) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagem = imagem;
    }


    public int getId() {return id;}

    public void setId(int nome) {
        this.id = id;
    }

    public String getNome() {return nome;}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPreco() { return preco;}

    public void setPreco(String preco) {this.preco = preco;}

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
