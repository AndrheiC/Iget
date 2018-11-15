package br.com.smarttech.acs.iget.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "TB_Compra",
        foreignKeys = {@ForeignKey(entity = Pessoa.class, parentColumns = "ID", childColumns = "idPessoa")})
public class Compra {

//    @Ignore
//    CompraRepository repository = new CompraRepository(this);

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IDCompra")
    private int id;
    private String valor;
    private String dataCompra;
    private String dataColeta;
    private String horaColeta;
    private int qtd;

    @ColumnInfo(name = "idPessoa")
    private String idPessoa;

    @Ignore
    private List<Produto> produtoList = new ArrayList<>();

    public Compra(String valor, String dataCompra, String dataColeta, String horaColeta, int qtd, String idPessoa, List<Produto> produtoList) {
        this.id = id;
        this.idPessoa = idPessoa;
        this.valor = valor;
        this.dataCompra = dataCompra;
        this.dataColeta = dataColeta;
        this.horaColeta = horaColeta;
        this.qtd = qtd;
        this.produtoList = produtoList;
    }

    public Compra(@NonNull int id, @NonNull String idPessoa,  String valor, String dataCompra, String dataColeta, String horaColeta, int qtd) {
        this.id = id;
        this.idPessoa = idPessoa;
        this.valor = valor;
        this.dataCompra = dataCompra;
        this.dataColeta = dataColeta;
        this.horaColeta = horaColeta;
        this.qtd = qtd;

    }

    public Compra() {
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(String dataColeta) {
        this.dataColeta = dataColeta;
    }

    public String getHoraColeta() {
        return horaColeta;
    }

    public void setHoraColeta(String horaColeta) {
        this.horaColeta = horaColeta;
    }

    public String getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(String idPessoa) {
        this.idPessoa = idPessoa;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }
}
