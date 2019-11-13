package com.example.tpfinal_grupal_mobil.entidades;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tpfinal_grupal_mobil.entidades.categoria.Categoria;
import com.example.tpfinal_grupal_mobil.entidades.distribuidora.distribuidora;
import com.example.tpfinal_grupal_mobil.entidades.usuario.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class entornodedatos
{
    public static final String LinkServidorWeb = "http://192.168.1.160:8080/ProyectoFinal_Grupal/";



    public static List<distribuidora> ListaDistribuidora = new ArrayList<distribuidora>();
    public static List<Usuario> ListaUsuario = new ArrayList<Usuario>();
    public static List<Categoria> ListaCategoria = new ArrayList<Categoria>();



    public static String AgregarParametroALink(String LinkServidorWebParametro,String NombreParametro,String ValorParametro)
    {
        String RutaFinal = LinkServidorWebParametro + NombreParametro + "=" + ValorParametro;
        return RutaFinal;
    }




    public static void BuscarDistribuidora(Context context, String DistibuidoraNombre)
    {

        RequestQueue queue = Volley.newRequestQueue(context);

        String RutaAlServicioWEB = entornodedatos.LinkServidorWeb + "/distribuidoraWS";

        RutaAlServicioWEB = entornodedatos.AgregarParametroALink(RutaAlServicioWEB,"?TipoProceso","4");
        RutaAlServicioWEB = entornodedatos.AgregarParametroALink(RutaAlServicioWEB,"&DistribuidoraNombreABuscar",DistibuidoraNombre);


        // La Dirección a donde vamos a realizar el Request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, RutaAlServicioWEB,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)

                    {
                        ConvertirJSONAListaDistribuidora(response);

                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

                //Toast.makeText(context,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        // Agregamos el StringRequest a la Cola de solicitudes //
        queue.add(stringRequest);
    }


    public static void ConvertirJSONAListaDistribuidora(String DatosEnFormatoJSON) {
        Gson Conversor = new Gson();

        Type listType = new TypeToken<List<distribuidora>>() {
        }.getType();
        entornodedatos.ListaDistribuidora.clear();
        entornodedatos.ListaDistribuidora = Conversor.fromJson(DatosEnFormatoJSON, listType);
    }





}
