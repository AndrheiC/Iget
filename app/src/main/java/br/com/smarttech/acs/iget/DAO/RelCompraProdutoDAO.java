package br.com.smarttech.acs.iget.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.smarttech.acs.iget.model.Compra;
import br.com.smarttech.acs.iget.model.Produto;
import br.com.smarttech.acs.iget.model.RelCompraProduto;


@Dao
public interface RelCompraProdutoDAO {

    @Insert
    void insert (RelCompraProduto relCompraProduto);

    @Query("SELECT * FROM rel_compraproduto")
    List <RelCompraProduto> getRelCompraProdutoAll();

    @Query("SELECT * FROM TB_Produto INNER JOIN REL_CompraProduto ON TB_Produto.ID = REL_CompraProduto.idProduto " +
            "WHERE REL_CompraProduto.idCompra=:idCompra")
    List<Produto> getProdutos (final int idCompra);

    @Query("SELECT * FROM TB_Compra INNER JOIN rel_compraproduto ON TB_Compra.IDCompra=rel_compraproduto.idCompra " +
            "WHERE rel_compraproduto.idProduto=:idProduto")
    List<Compra> getCompras(final int idProduto);

    @Query("SELECT * FROM TB_Compra INNER JOIN rel_compraproduto ON TB_Compra.IDCompra=rel_compraproduto.idCompra " +
            "WHERE rel_compraproduto.idProduto=:idProduto")
    List<CompraProduto> getCompraProduto(final int idProduto);

    static class CompraProduto{
        @Embedded
        Compra compra;
        @Embedded
        Produto produto;
    }
}
