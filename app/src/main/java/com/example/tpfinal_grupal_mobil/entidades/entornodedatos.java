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
    public static final String LinkServidorWeb = "http://192.168.1.170:8080/ProyectoFinal_Grupal/";

    public static boolean DatosInicializados = false;

    public static List<distribuidora> ListaDistribuidora = new ArrayList<distribuidora>();
    public static List<Categoria> ListaCategoria = new ArrayList<Categoria>();
    public static List<Usuario> ListaUsuario = new ArrayList<Usuario>();


    public static String AgregarParametroALink(String LinkServidorWebParametro, String NombreParametro, String ValorParametro)
    {
        String RutaFinal = LinkServidorWebParametro + NombreParametro + "=" + ValorParametro;
        return RutaFinal;
    }
/*
    public static void Inicializar()
    {
        if (!DatosInicializados)
        {
            distribuidora Categoria1 = new distribuidora(Long.valueOf(9), "262626", "Popeye", "Santa Marta");
            distribuidora Categoria2 = new distribuidora(Long.valueOf(10), "343434", "Nestle", "Santa Marta 434");
            distribuidora Categoria3 = new distribuidora(Long.valueOf(11), "342323", "Arcorsito", "Santa Marta 222");
            distribuidora Categoria4 = new distribuidora(Long.valueOf(12), "12221", "Lym", "Santa Marta 111");

            ListaDistribuidora.add(Categoria1);
            ListaDistribuidora.add(Categoria2);
            ListaDistribuidora.add(Categoria3);
            ListaDistribuidora.add(Categoria4);
        }

        DatosInicializados = true;

    }
*/


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

   /* public static void BuscarUsuario(Context context, String UsuarioNombre)
    {

        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());

        String RutaAlServicioWEB = LinkServidorWeb + "/usuariowsp";

        RutaAlServicioWEB = AgregarParametroALink(RutaAlServicioWEB,"?TipoProceso","4");
        RutaAlServicioWEB = AgregarParametroALink(RutaAlServicioWEB,"&ProductoNombreABuscar",UsuarioNombre);


        // La Dirección a donde vamos a realizar el Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RutaAlServicioWEB,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        ConvertirJSONALista(response);
                     //   UsuarioCargado = true;
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

              //  UsuarioCargado = false;

                //Toast.makeText(context,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        // Agregamos el StringRequest a la Cola de solicitudes //
        queue.add(stringRequest);

       // return UsuarioCargado;
    }


    public static void ConvertirJSONALista(String DatosEnFormatoJSON)
    {
        Gson Conversor = new Gson();

        Type listType = new TypeToken<List<Usuario>>(){}.getType();
        entornodedatos.ListaUsuario.clear();
        entornodedatos.ListaUsuario = Conversor.fromJson(DatosEnFormatoJSON, listType);



        // MyUsuarioAdapter.notifyDataSetChanged();

    }*/





}
