package br.com.smarttech.acs.iget.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.smarttech.acs.iget.DAO.CompraDAO;
import br.com.smarttech.acs.iget.DAO.ProdutoDAO;
import br.com.smarttech.acs.iget.DAO.RelCompraProdutoDAO;
import br.com.smarttech.acs.iget.model.Compra;
import br.com.smarttech.acs.iget.model.Pessoa;
import br.com.smarttech.acs.iget.model.Produto;
import br.com.smarttech.acs.iget.model.RelCompraProduto;

@Database(entities = {Compra.class, Produto.class, Pessoa.class, RelCompraProduto.class}, version = 1)
public abstract class RelCompraProdutoRoomDatabase extends RoomDatabase {
    private static volatile RelCompraProdutoRoomDatabase INSTANCE;
    public abstract RelCompraProdutoDAO relCompraProdutoDAO();
    public  abstract CompraDAO compraDAO();
    public abstract ProdutoDAO produtoDAO();

    public static RelCompraProdutoRoomDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (RelCompraProdutoRoomDatabase.class){
                if(INSTANCE==null){
                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(), RelCompraProdutoRoomDatabase.class, "REL_CompraProduto_database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
