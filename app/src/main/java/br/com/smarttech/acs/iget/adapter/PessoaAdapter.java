package br.com.smarttech.acs.iget.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.List;

import br.com.smarttech.acs.iget.model.Pessoa;

public class PessoaAdapter extends ArrayAdapter {
    private int rId;

    public PessoaAdapter(@NonNull Context context, int resource, @NonNull List<Pessoa>objects){
        super(context, resource, objects);
        rId=resource;
    }


    @Override
    public View getView(int position, View currentView, ViewGroup parent) {
        View mView=currentView;

        if (mView==null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView=inflater.inflate(rId, null);
        }

        Pessoa pessoa= (Pessoa) getItem(position);

        EditText editTextNome = (EditText) super.getView(position, currentView, parent);
        editTextNome.setText(pessoa.getNome());

        EditText editTextNascimento = (EditText) super.getView(position, currentView, parent);
        editTextNascimento.setText(pessoa.getNascimento());

        EditText editTextCartaoCredito = (EditText) super.getView(position, currentView, parent);
        editTextCartaoCredito.setText(pessoa.getCartaoCredito());

        EditText editTextValidadeCartao = (EditText) super.getView(position, currentView, parent);
        editTextValidadeCartao.setText(pessoa.getValidadeCartao());

        EditText editTextCvv = (EditText) super.getView(position, currentView, parent);
        editTextCvv.setText(pessoa.getCvv());



        return super.getView(position, currentView, parent);
    }
}
