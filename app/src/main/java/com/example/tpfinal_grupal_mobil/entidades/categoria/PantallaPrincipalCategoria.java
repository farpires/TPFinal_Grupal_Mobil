package com.example.tpfinal_grupal_mobil.entidades.categoria;

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
import com.example.tpfinal_grupal_mobil.entidades.entornodedatos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PantallaPrincipalCategoria extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener
{



    EditText categorianombre;

    Button BtnAgregar;
    Button BtnBusqueda;

    ListView Listado_List_view;
    CategoriaAdapter MyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principalcategoria);
        categorianombre = findViewById(R.id.categorianombre);

        BtnAgregar= findViewById(R.id.BtnAgregar);
        BtnBusqueda= findViewById(R.id.BtnBusqueda);

        BtnAgregar.setOnClickListener(this);
        BtnBusqueda.setOnClickListener(this);

        Listado_List_view = findViewById(R.id.Listado_List_view);
        MyAdapter = new CategoriaAdapter(getApplicationContext());
        Listado_List_view.setAdapter(MyAdapter);
        Listado_List_view.setOnItemClickListener(this);
        //entornodedatos.Inicializar();
    }

    private boolean Validar()
    {
        if (categorianombre.getText().toString().isEmpty())
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
            String Info = categorianombre.getText().toString();
            RequestQueue queue = Volley.newRequestQueue(this);

            String RutaAlServicioWEB =  "http://192.168.1.160:8080/ProyectoFinal_Grupal/categoriaWS?TipoProceso=4" + "&CategoriaNombreABuscar=" + Info;
            // La Dirección a donde vamos a realizar el Request
            StringRequest stringRequest = new StringRequest(Request.Method.GET, RutaAlServicioWEB,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                            Gson Conversor = new Gson();

                            Type listType = new TypeToken<List<Categoria>>(){}.getType();

                            entornodedatos.ListaCategoria.clear();
                            entornodedatos.ListaCategoria = Conversor.fromJson(response, listType);

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

            Intent IntentoAgregar = new Intent(PantallaPrincipalCategoria.this, CategoriaABM.class);

            IntentoAgregar.putExtra("PK", Long.valueOf(0));
            IntentoAgregar.putExtra("POSICION",0);

            startActivityForResult(IntentoAgregar,1);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Intent IntentoAgregar = new Intent(this, CategoriaABM.class);

        IntentoAgregar.putExtra("PK",l);
        IntentoAgregar.putExtra("POSICION",i);

        startActivityForResult(IntentoAgregar,2);
    }


    private void PersistirDatosEnLaNube(Categoria P,String TipoProceso)
    {

        Gson Conversor = new Gson();

        String SalidaDeObjetoEnFormatoJson = Conversor.toJson(P);

        Toast.makeText(getApplicationContext(),SalidaDeObjetoEnFormatoJson,Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(this);

        String RutaAlServicioWEB = entornodedatos.LinkServidorWeb + "/categoriaWS";

        RutaAlServicioWEB = entornodedatos.AgregarParametroALink(RutaAlServicioWEB,"?TipoProceso",TipoProceso);

        RutaAlServicioWEB = entornodedatos.AgregarParametroALink(RutaAlServicioWEB,"&ParametroJSON",SalidaDeObjetoEnFormatoJson);


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
