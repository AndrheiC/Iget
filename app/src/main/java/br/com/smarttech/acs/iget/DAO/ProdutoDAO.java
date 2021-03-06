package br.com.smarttech.acs.iget.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.smarttech.acs.iget.model.Pessoa;
import br.com.smarttech.acs.iget.model.Produto;

@Dao
public interface ProdutoDAO {

    @Insert
    void insert(Produto produto);

    @Update
    void update (Produto produto);

    @Query("DELETE FROM TB_Produto where NOME==:nome")
    void delete(String nome);

    @Query("SELECT * FROM TB_Produto WHERE ID==:id")
    Produto recuperarDadosProduto (int id);

    //@Query("SELECT * FROM tb_produto")
    //List<Integer> recuperaIds ();

    @Query("SELECT nome from TB_Produto")
    List<String> loadNames();

    @Query("SELECT ID, nome, descricao,preco,imagem from TB_Produto ORDER BY ID ASC")
    List<Produto> loadProdutos();


}
