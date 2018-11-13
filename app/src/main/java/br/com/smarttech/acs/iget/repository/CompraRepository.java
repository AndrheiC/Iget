package br.com.smarttech.acs.iget.repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.smarttech.acs.iget.DAO.CompraDAO;
import br.com.smarttech.acs.iget.database.CompraRoomDatabase;
import br.com.smarttech.acs.iget.model.Compra;


public class CompraRepository {
    private CompraDAO mCompraDAO;
    private List<Compra> mCompras;
    private List<CompraDAO.CompraPessoaJoin> mcompraPessoaJoinList;
    private List<Integer>mIds;
    private int idCompra;
    private Compra mCompra;

    public CompraRepository(Context context){
        CompraRoomDatabase db = CompraRoomDatabase.getDatabase(context);
        mCompraDAO = db.compraDAO();
    }

    public Compra compraPorId(int ID){

        return mCompraDAO.recuperarDadosCompra(ID);
    }

    public List<Compra> getAllCompra(){
        mcompraPessoaJoinList = mCompraDAO.compraProdutoJoin();
        return mCompras;
    }

    public void insert(Compra compra){

        new CompraRepository.insertAsyncTask(mCompraDAO).execute(compra);
    }

    public void update(Compra compra){

        new CompraRepository.updateAsyncTask(mCompraDAO).execute(compra);
    }

    public void delete(int idCompra){

        mCompraDAO.delete(idCompra);
    }



    private static class insertAsyncTask extends AsyncTask<Compra,Void, Void> {
        private CompraDAO mAsyncTaskDAO;
        insertAsyncTask(CompraDAO dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Compra...params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Compra,Void, Void>{
        private CompraDAO mAsyncTaskDAO;
        updateAsyncTask(CompraDAO dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Compra...params) {
            mAsyncTaskDAO.update(params[0]);
            return null;
        }
    }
}
