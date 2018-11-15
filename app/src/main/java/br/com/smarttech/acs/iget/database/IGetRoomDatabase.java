package br.com.smarttech.acs.iget.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.smarttech.acs.iget.DAO.CompraDAO;
import br.com.smarttech.acs.iget.DAO.PessoaDAO;
import br.com.smarttech.acs.iget.DAO.ProdutoDAO;
import br.com.smarttech.acs.iget.DAO.RelCompraProdutoDAO;
import br.com.smarttech.acs.iget.model.Compra;
import br.com.smarttech.acs.iget.model.Pessoa;
import br.com.smarttech.acs.iget.model.Produto;
import br.com.smarttech.acs.iget.model.RelCompraProduto;


@Database(entities = {Compra.class, Pessoa.class, Produto.class, RelCompraProduto.class},version = 3)
public abstract class IGetRoomDatabase extends RoomDatabase {
    private static volatile IGetRoomDatabase INSTANCE;
    public  abstract CompraDAO compraDAO();
    public abstract PessoaDAO pessoaDAO();
    public abstract ProdutoDAO produtoDAO();
    public abstract RelCompraProdutoDAO relCompraProdutoDAO();


    public static IGetRoomDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (IGetRoomDatabase.class){
                if(INSTANCE==null){
                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(), IGetRoomDatabase.class, "IGet_Database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
