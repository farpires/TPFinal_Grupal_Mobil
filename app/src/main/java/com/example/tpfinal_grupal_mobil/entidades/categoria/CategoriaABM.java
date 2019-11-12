package com.example.tpfinal_grupal_mobil.entidades.categoria;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.tpfinal_grupal_mobil.R;
import com.example.tpfinal_grupal_mobil.entidades.distribuidora.distribuidoraADAPTER;
import com.example.tpfinal_grupal_mobil.entidades.entornodedatos;
import com.google.gson.Gson;

public class CategoriaABM extends AppCompatActivity implements View.OnClickListener
{

    EditText CategoriaABMPK;
    EditText NombreCategoria;


    Button categoriaBotonEditar;
    Button categoriaABMBotonEliminar;

    Spinner DistribuidoraSpinner;
    distribuidoraADAPTER MyDistribuidoraAdapter;

    Long          PK =Long.valueOf(0);
    int           POSICION = 0;

    Categoria CategoriaMuestra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoriaabm);
        CategoriaABMPK = findViewById(R.id.CategoriaABMPK);
        NombreCategoria = findViewById(R.id.NombreCategoria);
        categoriaBotonEditar = findViewById(R.id.categoriaBotonEditar);
        categoriaABMBotonEliminar = findViewById(R.id.categoriaABMBotonEliminar);

        categoriaBotonEditar.setOnClickListener(this);
        categoriaABMBotonEliminar.setOnClickListener(this);


        DistribuidoraSpinner = findViewById(R.id.DistribuidoraSpinner);
        MyDistribuidoraAdapter = new distribuidoraADAPTER(getApplicationContext());
        DistribuidoraSpinner.setAdapter(MyDistribuidoraAdapter);

        PK = getIntent().getLongExtra("PK", Long.valueOf(0));
        POSICION = getIntent().getIntExtra("POSICION", 0);


        if (PK > 0) // POR AQUI SE INGRESA MODIFICANDO O ELIMINANDO UN PRODUCTO YA EXISTENTE //
        {

            CategoriaMuestra = entornodedatos.ListaCategoria.get(POSICION);

            CategoriaABMPK.setText(String.valueOf(PK));
            NombreCategoria.setText(CategoriaMuestra.getCategoriaNombre());


            int PosicionDeLaCategoriaAMostrarDentroDelSpinner = 0;

            for (int i = 0; i < entornodedatos.ListaDistribuidora.size(); i++) {
                if (entornodedatos.ListaDistribuidora.get(i).getIddistribuidora() == CategoriaMuestra.getIDDistribuidora()) {
                    PosicionDeLaCategoriaAMostrarDentroDelSpinner = i;
                }
            }

            DistribuidoraSpinner.setSelection(PosicionDeLaCategoriaAMostrarDentroDelSpinner, true);

            categoriaBotonEditar.setEnabled(true);
        } else // POR AQUI SE INGRESA CUANDO ES UN PRODUCTO NUEVO //
        {
            CategoriaMuestra = new Categoria();

            categoriaBotonEditar.setEnabled(true);
            categoriaABMBotonEliminar.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == categoriaABMBotonEliminar.getId())
        {


            String idcategoria = CategoriaABMPK.getText().toString();
            String categorianombre = NombreCategoria.getText().toString();

            Long iddistribuidora1 = DistribuidoraSpinner.getSelectedItemId();



            Categoria DistribuidoraModificar= new Categoria(Long.valueOf(idcategoria),categorianombre,iddistribuidora1);

            Gson Conversor = new Gson();

            String SalidaDeObjetoEnFormatoJson = Conversor.toJson(DistribuidoraModificar);

            RequestQueue queue = Volley.newRequestQueue(this);

            String RutaAlServicioWEB = entornodedatos.LinkServidorWeb + "/categoriaWS";

            RutaAlServicioWEB = entornodedatos.AgregarParametroALink(RutaAlServicioWEB,"?TipoProceso","3");

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


            queue.add(stringRequest);

        }
        if (v.getId()==categoriaBotonEditar.getId())
        {
            String categorianombre = NombreCategoria.getText().toString();
            Long iddistribuidora1 = DistribuidoraSpinner.getSelectedItemId();


            Categoria DistribuidoraModificar= new Categoria(PK,categorianombre,iddistribuidora1);



            Gson Conversor = new Gson();

            String SalidaDeObjetoEnFormatoJson = Conversor.toJson(DistribuidoraModificar);

            Toast.makeText(getApplicationContext(),SalidaDeObjetoEnFormatoJson,Toast.LENGTH_LONG).show();

            RequestQueue queue = Volley.newRequestQueue(this);

            String RutaAlServicioWEB = entornodedatos.LinkServidorWeb + "/categoriaWS";

            RutaAlServicioWEB = entornodedatos.AgregarParametroALink(RutaAlServicioWEB,"?TipoProceso","2");

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
}
