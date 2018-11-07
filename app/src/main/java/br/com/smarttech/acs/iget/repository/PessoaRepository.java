package br.com.smarttech.acs.iget.repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.smarttech.acs.iget.DAO.PessoaDAO;
import br.com.smarttech.acs.iget.database.PessoaRoomDatabase;
import br.com.smarttech.acs.iget.model.Pessoa;

public class PessoaRepository {
    private PessoaDAO mPessoaDAO;
    private List<Pessoa> mPessoas;

    public PessoaRepository(Context context){
        PessoaRoomDatabase db = PessoaRoomDatabase.getDatabase(context);
        mPessoaDAO = db.pessoaDAO();
        mPessoas = mPessoaDAO.loadPessoas();
    }
    public List<Pessoa> getAllPessoas() {
        return mPessoas;
    }

    public Pessoa pessoaPorId(String ID){
        return mPessoaDAO.recuperarDadosUsuario(ID);
    }

    public void insert(Pessoa pessoa){
        new insertAsyncTask(mPessoaDAO).execute(pessoa);
    }



    private static class insertAsyncTask extends AsyncTask<Pessoa,Void, Void>{
        private PessoaDAO mAsyncTaskDAO;
        insertAsyncTask(PessoaDAO dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Pessoa...params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }
}
