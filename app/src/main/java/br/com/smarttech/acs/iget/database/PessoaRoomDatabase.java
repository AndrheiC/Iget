package br.com.smarttech.acs.iget.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.smarttech.acs.iget.DAO.PessoaDAO;
import br.com.smarttech.acs.iget.model.Pessoa;

@Database(entities = {Pessoa.class},version = 1)
public abstract class PessoaRoomDatabase extends RoomDatabase {
    private static volatile PessoaRoomDatabase INSTANCE;
    public  abstract PessoaDAO pessoaDAO();

    public static PessoaRoomDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (PessoaRoomDatabase.class){
                if(INSTANCE==null){
                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(), PessoaRoomDatabase.class, "pessoa_database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
