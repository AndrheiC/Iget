package br.com.smarttech.acs.iget.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.smarttech.acs.iget.DAO.ProdutoDAO;
import br.com.smarttech.acs.iget.model.Produto;

@Database(entities = {Produto.class},version = 1)
public abstract class ProdutoRoomDatabase extends RoomDatabase {
    private static volatile ProdutoRoomDatabase INSTANCE;
    public  abstract ProdutoDAO produtoDAO();

    public static ProdutoRoomDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (ProdutoRoomDatabase.class){
                if(INSTANCE==null){
                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(), ProdutoRoomDatabase.class, "produto_database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
