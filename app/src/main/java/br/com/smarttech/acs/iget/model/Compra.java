package br.com.smarttech.acs.iget.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "TB_Compra")
public class Compra {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "ID")
    private String id;
    private String nome;
    private String valor;
    private String dataCompra;
    private String dataColeta;
    private String horaColeta;

    public Compra() {
    }

    public Compra(@NonNull String id, String nome, String valor, String dataCompra, String dataColeta, String horaColeta) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.dataCompra = dataCompra;
        this.dataColeta = dataColeta;
        this.horaColeta = horaColeta;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
}
