package com.example.tpfinal_grupal_mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tpfinal_grupal_mobil.entidades.distribuidora.distribuidoraprueba;

public class principal extends AppCompatActivity implements View.OnClickListener{

    Button BotonDistribuididora;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        BotonDistribuididora = findViewById(R.id.botondistribuidora);
        BotonDistribuididora.setOnClickListener(this);



    }

    @Override
    public void onClick(View v)
    {
        if (v.getId()==BotonDistribuididora.getId())
        {
            Intent IraDistribuidora = new Intent(this, distribuidoraprueba.class);
            startActivity(IraDistribuidora);
        }
    }
}
