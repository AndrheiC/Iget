package br.com.smarttech.acs.iget.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.smarttech.acs.iget.DAO.CompraDAO;
import br.com.smarttech.acs.iget.model.Compra;
import br.com.smarttech.acs.iget.model.Pessoa;


@Database(entities = {Compra.class, Pessoa.class},version = 1)
public abstract class CompraRoomDatabase extends RoomDatabase {
    private static volatile CompraRoomDatabase INSTANCE;
    public  abstract CompraDAO compraDAO();

    public static CompraRoomDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (CompraRoomDatabase.class){
                if(INSTANCE==null){
                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(), CompraRoomDatabase.class, "TB_Compra_database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
