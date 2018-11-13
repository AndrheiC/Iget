package br.com.smarttech.acs.iget.repository;


import android.content.Context;


public class Repository {
    private PessoaRepository pessoaRepository;
    private CompraRepository compraRepository;
    private ProdutoRepository produtoRepository;
    private RelCompraProdutoRepository relCompraProdutoRepository;

    public Repository(Context context){
        pessoaRepository = new PessoaRepository(context);
        compraRepository = new CompraRepository(context);
        produtoRepository = new ProdutoRepository(context);
        relCompraProdutoRepository = new RelCompraProdutoRepository(context);

    }

    public PessoaRepository getPessoaRepository(){
        return pessoaRepository;
    }

    public ProdutoRepository getProdutoRepository(){
        return produtoRepository;
    }


    public CompraRepository getCompraRepository(){
        return compraRepository;
    }

    public RelCompraProdutoRepository getRelCompraProdutoRepository(){
        return relCompraProdutoRepository;
    }

}
