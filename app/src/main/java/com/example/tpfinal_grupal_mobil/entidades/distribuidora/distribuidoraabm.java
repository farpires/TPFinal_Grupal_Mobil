package com.example.tpfinal_grupal_mobil.entidades.distribuidora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tpfinal_grupal_mobil.R;
import com.example.tpfinal_grupal_mobil.entidades.entornodedatos;
import com.google.gson.Gson;

public class distribuidoraabm extends AppCompatActivity implements View.OnClickListener{

    EditText DistribuidoraABMPK;
    EditText Distribuidoracuit;
    EditText DistribuidoraNombre;
    EditText DistribuidoraDomicilio;
    Button distribuidoraBotonAgregar;
    Button distribuidoraABMBotonEliminar;


    Long          PK =Long.valueOf(0);
    int           Posicion = 0;

    distribuidora distribuidoramuestra;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distribuidoraabm);

        DistribuidoraABMPK = findViewById(R.id.DistribuidoraABMPK);
        Distribuidoracuit = findViewById(R.id.Distribuidoracuit);
        DistribuidoraDomicilio = findViewById(R.id.DistribuidoraDomicilio);
        DistribuidoraNombre = findViewById(R.id.DistribuidoraNombre);
        distribuidoraABMBotonEliminar = findViewById(R.id.distribuidoraABMBotonEliminar);
        distribuidoraBotonAgregar = findViewById(R.id.distribuidoraBotonAgregar);

        distribuidoraBotonAgregar.setOnClickListener(this);
        distribuidoraABMBotonEliminar.setOnClickListener(this);

        PK = getIntent().getLongExtra("PK",Long.valueOf(0));
        Posicion = getIntent().getIntExtra("Posicion",0);
        Toast.makeText(getApplicationContext(),"Los Son " + PK + " Y " + Posicion,Toast.LENGTH_LONG).show();


        if (PK>0)
        {
            distribuidoramuestra = entornodedatos.ListaDistribuidora.get(Posicion);
            DistribuidoraABMPK.setText(String.valueOf(PK));
            DistribuidoraNombre.setText(distribuidoramuestra.getDistribuidoranombre());
        }
    }

    private boolean Validar()
    {
        if (DistribuidoraABMPK.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Falta Ingresar un Valor",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v)
    {
        if (v.getId()==distribuidoraBotonAgregar.getId())
        {
            String cuitDistribuidora = Distribuidoracuit.getText().toString();
            String distribuidoranombre = DistribuidoraNombre.getText().toString();
            String DistribuidoraDom = DistribuidoraDomicilio.getText().toString();

            distribuidora distribuidoramodificar = new distribuidora(PK,cuitDistribuidora,distribuidoranombre,DistribuidoraDom);

            Gson Conversor = new Gson();

            String SalidaDeObjetoEnFormatoJson = Conversor.toJson(distribuidoramodificar);

            RequestQueue queue = Volley.newRequestQueue(this);

            String RutaAlServicioWEB = entornodedatos.LinkServidorWeb + "/distribuidoraWS";

            RutaAlServicioWEB = entornodedatos.AgregarParametroALink(RutaAlServicioWEB,"?TipoProceso","2");
            RutaAlServicioWEB = entornodedatos.AgregarParametroALink(RutaAlServicioWEB,"&DatosEnFormatoJSON",SalidaDeObjetoEnFormatoJson);

            // La Dirección a donde vamos a realizar el Request
            StringRequest stringRequest = new StringRequest(Request.Method.GET, RutaAlServicioWEB,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(getApplicationContext(),error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });

            // Agregamos el StringRequest a la Cola de solicitudes //
            queue.add(stringRequest);




        }
        if (v.getId()== distribuidoraABMBotonEliminar.getId())
        {
            if (Validar())
            {


                String Info = DistribuidoraABMPK.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(this);

                String RutaAlServicioWEB =  "http://192.168.1.150:8080/ProyectoFinal_Grupal/distribuidoraWS?TipoProceso=3" + "&DatosEnFormatoJSON=" + Info;

                // La Dirección a donde vamos a realizar el Request
                StringRequest stringRequest = new StringRequest(Request.Method.GET, RutaAlServicioWEB,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(),error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

                // Agregamos el StringRequest a la Cola de solicitudes //
                queue.add(stringRequest);

            }
        }
    }
}
