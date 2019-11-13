package com.example.tpfinal_grupal_mobil.entidades.usuario;

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


public class UsuarioBuscar extends AppCompatActivity implements AdapterView.OnItemClickListener
{

    EditText Usuario_ABM_EdtBusqueda;
    Button Usuario_ABM_BtnBusqueda;

    ListView Usuario_ABM_Lista;
    usuarioadapter MyUsuarioAdapter;

    Button Usuario_ABM_BtnAgregarNuevoUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuariobusqueda);

        Usuario_ABM_EdtBusqueda=findViewById(R.id.Usuario_ABM_EdtBusqueda);
        Usuario_ABM_BtnBusqueda=findViewById(R.id.Usuario_ABM_BtnBusqueda);
        Usuario_ABM_Lista=findViewById(R.id.Usuario_ABM_Lista);
        Usuario_ABM_BtnAgregarNuevoUsuario=findViewById(R.id.Usuario_ABM_BtnAgregarNuevoUsuario);

        MyUsuarioAdapter = new usuarioadapter(getApplicationContext());
        Usuario_ABM_Lista.setAdapter(MyUsuarioAdapter);

        Usuario_ABM_Lista.setOnItemClickListener(this);

        Usuario_ABM_BtnBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //LISTAR SOLO EL USUARIO QUE NECESITE

              BuscarProducto(Usuario_ABM_EdtBusqueda.getText().toString());
            }
        });
        Usuario_ABM_BtnAgregarNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent IntentoAgregar = new Intent(UsuarioBuscar.this, usuarioabm.class);

                IntentoAgregar.putExtra("PK", Long.valueOf(0));
                IntentoAgregar.putExtra("POSICION",0);

                startActivityForResult(IntentoAgregar,1);
            }
        });




    }

   private void BuscarProducto(String ValorBuscado)
    {

        RequestQueue queue = Volley.newRequestQueue(this);

        String RutaAlServicioWEB = entornodedatos.LinkServidorWeb + "/usuariowsp";

        RutaAlServicioWEB = entornodedatos.AgregarParametroALink(RutaAlServicioWEB,"?TipoProceso","4");
        RutaAlServicioWEB = entornodedatos.AgregarParametroALink(RutaAlServicioWEB,"&ProductoNombreABuscar",ValorBuscado);


        // La Dirección a donde vamos a realizar el Request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, RutaAlServicioWEB,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                       ConvertirJSONALista(response);
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

    public void ConvertirJSONALista(String DatosEnFormatoJSON)
    {
        Gson Conversor = new Gson();

        Type listType = new TypeToken<List<Usuario>>(){}.getType();

        entornodedatos.ListaUsuario.clear();
        entornodedatos.ListaUsuario = Conversor.fromJson(DatosEnFormatoJSON, listType);

        MyUsuarioAdapter.notifyDataSetChanged();

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent IntentoAgregar = new Intent(this, usuarioabm.class);

        IntentoAgregar.putExtra("PK",l);
        IntentoAgregar.putExtra("POSICION",i);

        startActivityForResult(IntentoAgregar,2);
    }
}
