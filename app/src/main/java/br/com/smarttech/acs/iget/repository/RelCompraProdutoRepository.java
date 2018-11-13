package br.com.smarttech.acs.iget.repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.smarttech.acs.iget.DAO.CompraDAO;
import br.com.smarttech.acs.iget.DAO.RelCompraProdutoDAO;
import br.com.smarttech.acs.iget.database.CompraRoomDatabase;
import br.com.smarttech.acs.iget.database.RelCompraProdutoRoomDatabase;
import br.com.smarttech.acs.iget.model.Compra;
import br.com.smarttech.acs.iget.model.RelCompraProduto;

public class RelCompraProdutoRepository {
    private RelCompraProdutoDAO mRelCompraProdutoDAO;
    private List<RelCompraProduto> mRelCompraProdutos;
    private List<Integer>mIds;
    private RelCompraProduto mRelCompraProduto;

    public RelCompraProdutoRepository(Context context){
        RelCompraProdutoRoomDatabase db = RelCompraProdutoRoomDatabase.getDatabase(context);
        mRelCompraProdutoDAO = db.relCompraProdutoDAO();
    }

    public List<RelCompraProduto> getAllRelCompraProduto(){
        mRelCompraProdutos = mRelCompraProdutoDAO.getRelCompraProdutoAll();
        return mRelCompraProdutos;
    }

    public void insert(RelCompraProduto relCompraProduto){

        new RelCompraProdutoRepository.insertAsyncTask(mRelCompraProdutoDAO).execute(relCompraProduto);
    }


    private static class insertAsyncTask extends AsyncTask<RelCompraProduto,Void, Void> {
        private RelCompraProdutoDAO mAsyncTaskDAO;
        insertAsyncTask(RelCompraProdutoDAO dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final RelCompraProduto...params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }

}
