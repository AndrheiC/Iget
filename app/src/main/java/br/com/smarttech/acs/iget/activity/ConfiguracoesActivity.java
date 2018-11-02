package br.com.smarttech.acs.iget.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import br.com.smarttech.acs.iget.R;

public class ConfiguracoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        //Configura toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("IGet - Configurações");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
