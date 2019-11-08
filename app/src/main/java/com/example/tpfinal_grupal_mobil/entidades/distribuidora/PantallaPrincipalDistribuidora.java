package com.example.tpfinal_grupal_mobil.entidades.distribuidora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PantallaPrincipalDistribuidora extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener
{


    EditText Distribuidoracuit;
    EditText distribuidoranombre;
    EditText Distribuidoradomicilio;
    Button BtnAgregar;
    Button BtnBusqueda;

    ListView Listado_List_view;
    distribuidoraADAPTER MyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principaldistribuidora);
        distribuidoranombre = findViewById(R.id.distribuidoranombre);
        Distribuidoracuit = findViewById(R.id.Distribuidoracuit);
        Distribuidoradomicilio = findViewById(R.id.DistribuidoraDomicilio);
        BtnAgregar= findViewById(R.id.BtnAgregar);
        BtnBusqueda= findViewById(R.id.BtnBusqueda);

        BtnAgregar.setOnClickListener(this);
        BtnBusqueda.setOnClickListener(this);

        Listado_List_view = findViewById(R.id.Listado_List_view);
        MyAdapter = new distribuidoraADAPTER(getApplicationContext());
        Listado_List_view.setAdapter(MyAdapter);
        Listado_List_view.setOnItemClickListener(this);
        entornodedatosdistribuidora.Inicializar();
    }

    private boolean Validar()
    {
        if (distribuidoranombre.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Falta Ingresar un Valor", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v)
    {
        if (BtnBusqueda.getId() == v.getId())
        {
            String Info = distribuidoranombre.getText().toString();
            RequestQueue queue = Volley.newRequestQueue(this);

            String RutaAlServicioWEB =  "http://192.168.1.150:8080/ProyectoFinal_Grupal/distribuidoraWS?TipoProceso=4" + "&DistribuidoraNombreABuscar=" + Info;
            // La Dirección a donde vamos a realizar el Request
            StringRequest stringRequest = new StringRequest(Request.Method.GET, RutaAlServicioWEB,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                            Gson Conversor = new Gson();

                            Type listType = new TypeToken<List<distribuidora>>(){}.getType();

                            entornodedatosdistribuidora.ListaDistribuidora.clear();
                            entornodedatosdistribuidora.ListaDistribuidora = Conversor.fromJson(response, listType);

                            MyAdapter.notifyDataSetChanged();
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
        if (BtnAgregar.getId()==v.getId())
        {
            String distribuidoracuit = Distribuidoracuit.getText().toString();
            String distribuidoranombree = distribuidoranombre.getText().toString();
            String distribuidoradomici = Distribuidoradomicilio.getText().toString();

            distribuidora distribuidorainicial = new distribuidora(Long.valueOf(0),distribuidoracuit,distribuidoranombree,distribuidoradomici);
            PersistirDatosEnLaNube(distribuidorainicial,"1");

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Intent IntentoiradistribuidoraABM = new Intent(this, distribuidoraABM.class);
        IntentoiradistribuidoraABM.putExtra("PK",l);
        IntentoiradistribuidoraABM.putExtra("Posicion",i);
        startActivity(IntentoiradistribuidoraABM);
    }


    private void PersistirDatosEnLaNube(distribuidora P,String TipoProceso)
    {

        Gson Conversor = new Gson();

        String SalidaDeObjetoEnFormatoJson = Conversor.toJson(P);

        Toast.makeText(getApplicationContext(),SalidaDeObjetoEnFormatoJson,Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(this);

        String RutaAlServicioWEB = entornodedatosdistribuidora.LinkServidorWeb + "/distribuidoraWS";

        RutaAlServicioWEB = entornodedatosdistribuidora.AgregarParametroALink(RutaAlServicioWEB,"?TipoProceso",TipoProceso);

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
