package br.com.smarttech.acs.iget.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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

        



        return super.getView(position, currentView, parent);
    }
}
