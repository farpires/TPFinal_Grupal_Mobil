package com.example.tpfinal_grupal_mobil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tpfinal_grupal_mobil.entidades.distribuidora.PantallaPrincipalDistribuidora;

public class principal extends AppCompatActivity implements View.OnClickListener{

    Button BotonIrADistribuidora;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        BotonIrADistribuidora = findViewById(R.id.BotonIraDistribuidora);
        BotonIrADistribuidora.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId()==BotonIrADistribuidora.getId())
        {
            Intent iradistribuidora = new Intent(this, PantallaPrincipalDistribuidora.class);
            startActivity(iradistribuidora);
        }
    }
}
