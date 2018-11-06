package br.com.smarttech.acs.iget.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.smarttech.acs.iget.model.Pessoa;

@Dao
public interface PessoaDAO {

    @Insert
    void insert(Pessoa pessoa);

    @Query("DELETE FROM tb_pessoa where ID==:id")
    void delete(int id);

    @Query("SELECT * FROM TB_Pessoa ORDER BY nome DESC")
    List<Pessoa>loadPessoas();
}
