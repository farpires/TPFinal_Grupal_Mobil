package com.example.tpfinal_grupal_mobil.entidades;

import com.example.tpfinal_grupal_mobil.entidades.distribuidora.distribuidora;

import java.util.ArrayList;
import java.util.List;

public class entornodedatosdistribuidora {
    public static final String LinkServidorWeb = "http://192.168.1.150:8080/ProyectoFinal_Grupal/";
    public static boolean DatosInicializados = false;
    public static List<distribuidora> ListaDistribuidora = new ArrayList<distribuidora>();

    public static String AgregarParametroALink(String LinkServidorWebParametro, String NombreParametro, String ValorParametro) {
        String RutaFinal = LinkServidorWebParametro + NombreParametro + "=" + ValorParametro;
        return RutaFinal;
    }

    public static void Inicializar() {
        if (!DatosInicializados) {
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
}