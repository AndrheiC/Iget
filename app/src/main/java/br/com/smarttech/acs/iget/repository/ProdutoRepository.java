package br.com.smarttech.acs.iget.repository;

import android.content.Context;
import android.os.AsyncTask;


import br.com.smarttech.acs.iget.DAO.ProdutoDAO;
import br.com.smarttech.acs.iget.database.ProdutoRoomDatabase;
import br.com.smarttech.acs.iget.model.Produto;

public class ProdutoRepository {

    private ProdutoDAO mProdutoDAO;
    private Produto mProdutos;
    private int idProduto;

    public ProdutoRepository(Context context){
        ProdutoRoomDatabase db = ProdutoRoomDatabase.getDatabase(context);
        mProdutoDAO = db.produtoDAO();
        idProduto = mProdutos.getId();
        mProdutos = mProdutoDAO.recuperarDadosProduto(idProduto);
    }

    public Produto produtoPorId(int ID){

        return mProdutoDAO.recuperarDadosProduto(ID);
    }

    public void insert(Produto produto){

        new ProdutoRepository.insertAsyncTask(mProdutoDAO).execute(produto);
    }

    public void update(Produto produto){

        new ProdutoRepository.updateAsyncTask(mProdutoDAO).execute(produto);
    }



    private static class insertAsyncTask extends AsyncTask<Produto,Void, Void> {
        private ProdutoDAO mAsyncTaskDAO;
        insertAsyncTask(ProdutoDAO dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Produto...params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Produto,Void, Void>{
        private ProdutoDAO mAsyncTaskDAO;
        updateAsyncTask(ProdutoDAO dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Produto...params) {
            mAsyncTaskDAO.update(params[0]);
            return null;
        }
    }
}

