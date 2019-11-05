package com.example.tpfinal_grupal_mobil.entidades.distribuidora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tpfinal_grupal_mobil.R;

public class distribuidoraprueba extends AppCompatActivity implements View.OnClickListener
{

    EditText EdtDato;
    Button BtnAgregar;
    Button BtnBusqueda;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distribuidoraprueba);
    }

    @Override
    public void onClick(View v)
    {

    }
}
