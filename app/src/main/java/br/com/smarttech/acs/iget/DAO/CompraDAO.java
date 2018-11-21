package br.com.smarttech.acs.iget.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.smarttech.acs.iget.model.Compra;
import br.com.smarttech.acs.iget.model.Pessoa;
import br.com.smarttech.acs.iget.model.Produto;
import br.com.smarttech.acs.iget.model.RelCompraProduto;

@Dao
public interface CompraDAO {

    @Insert
    long insert(Compra compra);

    @Update
    void update (Compra compra);

    @Query("DELETE FROM tb_compra where IDCompra==:id")
    void delete(int id);

    @Query("SELECT * FROM TB_Compra WHERE idPessoa==:id")
    Compra recuperarDadosCompra (int id);

    @Query("SELECT tb_Compra.IDCompra, tb_Compra.valor, tb_Compra.dataCompra, tb_Compra.dataColeta, tb_Compra.horaColeta, tb_Compra.qtd, tb_Compra.idPessoa " +
            "FROM TB_Compra " +
            "WHERE TB_Compra.idPessoa == :idPessoa")
    List<Compra> compraProdutoJoin(String idPessoa);

    static class CompraPessoaJoin{
        @Embedded
        public Compra compra;
        @Embedded(prefix = "pessoa_")
        public Pessoa pessoa;
        @Embedded (prefix = "REL_")
        public RelCompraProduto relCompraProduto;
        @Embedded
        public Produto produto;
    }
}
