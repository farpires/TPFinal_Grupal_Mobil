package com.example.tpfinal_grupal_mobil.entidades.usuario;

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

public class usuarioabm extends AppCompatActivity implements View.OnClickListener {
    EditText idusuario;
    EditText usuarionombre;
    EditText usuariopassword;
    EditText usuarioemail;

    Spinner iddistribuidora;
    distribuidoraADAPTER MyDsitribuidoraAdapter;

    Button BTNUsuarioAgregar;
    Button BTNGuardarCambios;
    Button BTNEliminar;

    Long PK = Long.valueOf(0);
    int POSICION = 0;

    Usuario usuarioprincipal;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuarioabm);

        idusuario=findViewById(R.id.idusuario);
        idusuario.setEnabled(false);

        usuarionombre=findViewById(R.id.usuarionombre);
        usuariopassword=findViewById(R.id.usuariopassword);
        usuarioemail=findViewById(R.id.usuarioemail);
        iddistribuidora=findViewById(R.id.iddistribuidora);

        MyDsitribuidoraAdapter = new distribuidoraADAPTER(getApplicationContext());
        iddistribuidora.setAdapter(MyDsitribuidoraAdapter);

        BTNUsuarioAgregar=findViewById(R.id.BTNUsuarioAgregar);
        BTNGuardarCambios=findViewById(R.id.BTNGuardarCambios);
        BTNEliminar=findViewById(R.id.BTNEliminar);

        BTNUsuarioAgregar.setOnClickListener(this);
        BTNGuardarCambios.setOnClickListener(this);
        BTNEliminar.setOnClickListener(this);


        PK = getIntent().getLongExtra("PK", Long.valueOf(0));
        POSICION = getIntent().getIntExtra("POSICION",0);



        if (PK > 0) // POR AQUI SE INGRESA MODIFICANDO O ELIMINANDO UN PRODUCTO YA EXISTENTE //
        {

            usuarioprincipal = entornodedatos.ListaUsuario.get(POSICION);

            idusuario.setText(String.valueOf(PK));
            usuarionombre.setText(usuarioprincipal.getUsuarionombre());
            usuariopassword.setText(usuarioprincipal.getUsuariopassword());
            usuarioemail.setText(usuarioprincipal.getUsuarioemail());


            int PosicionDeLaCategoriaAMostrarDentroDelSpinner = 0;

            for (int i = 0; i < entornodedatos.ListaDistribuidora.size();i++)
            {
                if (entornodedatos.ListaDistribuidora.get(i).getIddistribuidora()== usuarioprincipal.getIddistribuidora())
                {
                    PosicionDeLaCategoriaAMostrarDentroDelSpinner = i;
                }
            }

            iddistribuidora.setSelection(PosicionDeLaCategoriaAMostrarDentroDelSpinner,true);

            BTNUsuarioAgregar.setEnabled(false);
        }
        else // POR AQUI SE INGRESA CUANDO ES UN PRODUCTO NUEVO //
        {
            usuarioprincipal = new Usuario();

            BTNGuardarCambios.setEnabled(false);
            BTNEliminar.setEnabled(false);
        }


    }
  @Override
    public void onClick(View v) {
            if(v.getId()== BTNUsuarioAgregar.getId()) {
          //  if (Validar()) {

                Long PPK = Long.valueOf(0);
                String uusuarionombre = usuarionombre.getText().toString();
                String uusuariopassword = usuariopassword.getText().toString();
                String uusuarioemail = usuarioemail.getText().toString();
                Long uiddistribuidora = iddistribuidora.getSelectedItemId();

                usuarioprincipal = new Usuario(Long.valueOf(0), uusuarionombre, uusuariopassword, uusuarioemail, uiddistribuidora);

                PersistirDatosEnLaNube(usuarioprincipal, "1");
           // }
            }
            if (v.getId() == BTNGuardarCambios.getId())
            {
                String uusuarionombre = usuarionombre.getText().toString();
                String uusuariopassword = usuariopassword.getText().toString();
                String uusuarioemail = usuarioemail.getText().toString();
                Long uiddistribuidora = iddistribuidora.getSelectedItemId();

                usuarioprincipal = new Usuario(PK,uusuarionombre,uusuariopassword,uusuarioemail,uiddistribuidora);

                PersistirDatosEnLaNube(usuarioprincipal,"2");
            }
            if (v.getId()==BTNEliminar.getId())
            {
                String uusuarionombre = usuarionombre.getText().toString();
                String uusuariopassword = usuariopassword.getText().toString();
                String uusuarioemail = usuarioemail.getText().toString();
                Long uiddistribuidora = iddistribuidora.getSelectedItemId();

                usuarioprincipal = new Usuario(PK,uusuarionombre,uusuariopassword,uusuarioemail,uiddistribuidora);


                PersistirDatosEnLaNube(usuarioprincipal,"3");
            }

        }


    private void PersistirDatosEnLaNube(Usuario P, String TipoProceso)
    {

        Gson Conversor = new Gson();

        String SalidaDeObjetoEnFormatoJson = Conversor.toJson(P);

       Toast.makeText(getApplicationContext(),SalidaDeObjetoEnFormatoJson, Toast.LENGTH_LONG).show();


        RequestQueue queue = Volley.newRequestQueue(this);

        String RutaAlServicioWEB = entornodedatos.LinkServidorWeb + "/usuariowsp";

        RutaAlServicioWEB = entornodedatos.AgregarParametroALink(RutaAlServicioWEB,"?TipoProceso",TipoProceso);

        RutaAlServicioWEB = entornodedatos.AgregarParametroALink(RutaAlServicioWEB,"&ParametroJSON",SalidaDeObjetoEnFormatoJson);


        // La Dirección a donde vamos a realizar el Request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, RutaAlServicioWEB,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(),error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Agregamos el StringRequest a la Cola de solicitudes //
        queue.add(stringRequest);

    }
    public void RecuperarDsitribuidora()
    {


    }

}
