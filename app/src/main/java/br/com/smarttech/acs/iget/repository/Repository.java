package br.com.smarttech.acs.iget.repository;


import android.content.Context;

public class Repository {
    private PessoaRepository pessoaRepository;
    //private CompraRepository compraRepository;

    public Repository(Context context){
        pessoaRepository = new PessoaRepository(context);
        //compraRepository = new CompraRepository(context);

    }

    public PessoaRepository getPessoaRepository(){
        return pessoaRepository;
    }

    /*
    public CompraRepository getCompraRepository(){
        return compraRepository;
    }
    */
}
