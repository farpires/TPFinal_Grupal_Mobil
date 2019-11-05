package com.example.tpfinal_grupal_mobil.entidades.distribuidora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class distribuidoraprueba extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener
{

    EditText EdtDato;
    Button BtnAgregar;
    Button BtnBusqueda;
    ListView Listado_List_view;
    distribuidoraadapter MyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distribuidoraprueba);
        EdtDato = findViewById(R.id.EdtDato);
        BtnAgregar= findViewById(R.id.BtnAgregar);
        BtnBusqueda= findViewById(R.id.BtnBusqueda);

        BtnAgregar.setOnClickListener(this);
        BtnBusqueda.setOnClickListener(this);

        Listado_List_view = findViewById(R.id.Listado_List_view);
        MyAdapter = new distribuidoraadapter(getApplicationContext());
        Listado_List_view.setAdapter(MyAdapter);
        Listado_List_view.setOnItemClickListener(this);

    }
    private boolean Validar()
    {
        if (EdtDato.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Falta Ingresar un Valor",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v)
    {
        if (BtnAgregar.getId()==v.getId())
        {

            if (Validar())
            {


                String Info = EdtDato.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(this);

                String RutaAlServicioWEB =  "http://192.168.1.150:8080/ProyectoFinal_Grupal/distribuidoraWS?TipoProceso=1" + "&DatosEnFormatoJSON=" + Info;


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
        if (BtnBusqueda.getId() == v.getId())
        {
            String Info = EdtDato.getText().toString();
            RequestQueue queue = Volley.newRequestQueue(this);

            String RutaAlServicioWEB =  "http://192.168.1.150:8080/ProyectoFinal_Grupal/distribuidoraWS?TipoProceso=4" + "&DatosEnFormatoJSON=" + Info;
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

                            entornodedatos.ListaDistribuidora.clear();
                            entornodedatos.ListaDistribuidora = Conversor.fromJson(response, listType);

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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent IntentoiraDistribuidoraABM= new Intent(this, distribuidora.class);
        IntentoiraDistribuidoraABM.putExtra("PK",id);
        IntentoiraDistribuidoraABM.putExtra("Posicion",position);
        startActivity(IntentoiraDistribuidoraABM);
    }
}
