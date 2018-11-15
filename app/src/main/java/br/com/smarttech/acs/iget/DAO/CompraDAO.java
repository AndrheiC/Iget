package br.com.smarttech.acs.iget.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.smarttech.acs.iget.model.Compra;
import br.com.smarttech.acs.iget.model.Pessoa;

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

    @Query("SELECT tb_Compra.IDCompra, tb_Compra.valor, tb_Compra.dataCompra, tb_Compra.dataColeta, tb_Compra.horaColeta, tb_pessoa.nome as pessoa_nome, REL_CompraProduto.idProduto as idProduto, REL_CompraProduto.idCompra as idCompraRelacional, tb_produto.nome as nomeProduto, tb_produto.descricao as descricaoProduto " +
            "FROM TB_Compra " +
            "INNER JOIN tb_pessoa ON tb_Compra.idPessoa=tb_pessoa.ID " +
            "INNER JOIN REL_CompraProduto ON TB_Compra.IDCompra =REL_CompraProduto.idCompra " +
            "INNER JOIN TB_Produto ON REL_CompraProduto.idProduto = TB_Produto.ID ")
    List<CompraPessoaJoin> compraProdutoJoin();

    static class CompraPessoaJoin{
        @Embedded
        public Compra compra;
        @Embedded(prefix = "pessoa_")
        public Pessoa pessoa;
    }
}
