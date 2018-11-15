package br.com.smarttech.acs.iget.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "REL_CompraProduto",
        primaryKeys = {"idProduto", "idCompra"},
        foreignKeys ={@ForeignKey(entity = Produto.class, parentColumns = "ID", childColumns = "idProduto", onDelete = CASCADE),
                      @ForeignKey(entity = Compra.class, parentColumns = "IDCompra", childColumns = "idCompra", onDelete = CASCADE)})
public class RelCompraProduto {
    public final int idProduto;
    public final int idCompra;

    public RelCompraProduto(int idProduto, int idCompra) {
        this.idProduto = idProduto;
        this.idCompra = idCompra;
    }
}
