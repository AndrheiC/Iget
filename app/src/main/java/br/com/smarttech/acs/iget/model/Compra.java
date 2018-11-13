package br.com.smarttech.acs.iget.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "TB_Compra",
        foreignKeys = {@ForeignKey(entity = Pessoa.class, parentColumns = "ID", childColumns = "idPessoa")})
public class Compra {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "ID")
    private int id;
    private String valor;
    private String dataCompra;
    private String dataColeta;
    private String horaColeta;

    @ColumnInfo(name = "idPessoa")
    private int idPessoa;

    public Compra() {
    }

    public Compra(@NonNull int id, @NonNull int idPessoa,  String valor, String dataCompra, String dataColeta, String horaColeta) {
        this.id = id;
        this.id = idPessoa;
        this.valor = valor;
        this.dataCompra = dataCompra;
        this.dataColeta = dataColeta;
        this.horaColeta = horaColeta;
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


    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }
}
