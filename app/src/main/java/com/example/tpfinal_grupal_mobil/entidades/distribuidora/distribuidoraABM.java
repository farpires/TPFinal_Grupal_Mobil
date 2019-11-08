package com.example.tpfinal_grupal_mobil.entidades.distribuidora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tpfinal_grupal_mobil.R;
import com.google.gson.Gson;

public class distribuidoraABM extends AppCompatActivity implements View.OnClickListener
{

    EditText DistribuidoraABMPK;
    EditText Distribuidoracuit;
    EditText DistribuidoraNombre;
    EditText DistribuidoraDomicilio;
    Button distribuidoraBotonEditar;
    Button distribuidoraABMBotonEliminar;

    Long          PK =Long.valueOf(0);
    int           Posicion = 0;

    distribuidora DistribuidoraMuestra;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distribuidoraabm);
        DistribuidoraABMPK = findViewById(R.id.DistribuidoraABMPK);
        Distribuidoracuit = findViewById(R.id.Distribuidoracuit);
        DistribuidoraNombre = findViewById(R.id.DistribuidoraNombre);
        DistribuidoraDomicilio = findViewById(R.id.DistribuidoraDomicilio);
        distribuidoraBotonEditar = findViewById(R.id.distribuidoraBotonEditar);
        distribuidoraABMBotonEliminar = findViewById(R.id.distribuidoraABMBotonEliminar);
        distribuidoraBotonEditar.setOnClickListener(this);
        distribuidoraABMBotonEliminar.setOnClickListener(this);

        PK = getIntent().getLongExtra("PK",Long.valueOf(0));
        Posicion = getIntent().getIntExtra("Posicion",0);
        Toast.makeText(getApplicationContext(),"Los Son " + PK + " Y " + Posicion,Toast.LENGTH_LONG).show();

        //ACA CAPTURO LOS VALORES

        if (PK>0)
        {
            DistribuidoraMuestra = entornodedatosdistribuidora.ListaDistribuidora.get(Posicion);
            DistribuidoraABMPK.setText(String.valueOf(PK));
            Distribuidoracuit.setText(DistribuidoraMuestra.getDistribuidoracuit());
            DistribuidoraNombre.setText(DistribuidoraMuestra.getDistribuidoranombre());
            DistribuidoraDomicilio.setText(DistribuidoraMuestra.getDistribuidoradomicilio());
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == distribuidoraABMBotonEliminar.getId())
        {


            String id = DistribuidoraABMPK.getText().toString();
            String cuit = Distribuidoracuit.getText().toString();
            String nombre = DistribuidoraNombre.getText().toString();
            String domicilio = DistribuidoraDomicilio.getText().toString();

            distribuidora DistribuidoraModificar= new distribuidora(Long.valueOf(id),cuit,nombre,domicilio);

            Gson Conversor = new Gson();

            String SalidaDeObjetoEnFormatoJson = Conversor.toJson(DistribuidoraModificar);

            RequestQueue queue = Volley.newRequestQueue(this);

            String RutaAlServicioWEB = entornodedatosdistribuidora.LinkServidorWeb + "/distribuidoraWS";

            RutaAlServicioWEB = entornodedatosdistribuidora.AgregarParametroALink(RutaAlServicioWEB,"?TipoProceso","3");

            RutaAlServicioWEB = entornodedatosdistribuidora.AgregarParametroALink(RutaAlServicioWEB,"&ParametroJSON",SalidaDeObjetoEnFormatoJson);


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


            queue.add(stringRequest);

        }
        if (v.getId()==distribuidoraBotonEditar.getId())
        {
            String cuit = Distribuidoracuit.getText().toString();
            String nombre = DistribuidoraNombre.getText().toString();
            String domicilio = DistribuidoraDomicilio.getText().toString();

            distribuidora DistribuidoraModificar= new distribuidora(PK,cuit,nombre,domicilio);


            Gson Conversor = new Gson();

            String SalidaDeObjetoEnFormatoJson = Conversor.toJson(DistribuidoraModificar);

            //Toast.makeText(getApplicationContext(),SalidaDeObjetoEnFormatoJson,Toast.LENGTH_LONG).show();

            RequestQueue queue = Volley.newRequestQueue(this);

            String RutaAlServicioWEB = entornodedatosdistribuidora.LinkServidorWeb + "/distribuidoraWS";

            RutaAlServicioWEB = entornodedatosdistribuidora.AgregarParametroALink(RutaAlServicioWEB,"?TipoProceso","2");

            RutaAlServicioWEB = entornodedatosdistribuidora.AgregarParametroALink(RutaAlServicioWEB,"&ParametroJSON",SalidaDeObjetoEnFormatoJson);


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
