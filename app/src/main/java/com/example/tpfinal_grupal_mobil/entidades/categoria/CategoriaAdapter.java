package com.example.tpfinal_grupal_mobil.entidades.categoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.tpfinal_grupal_mobil.R;
import com.example.tpfinal_grupal_mobil.entidades.entornodedatos;


public class CategoriaAdapter extends BaseAdapter
{
    Context context;

    public CategoriaAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return(entornodedatos.ListaCategoria.size());
    }

    @Override
    public Object getItem(int i)
    {
        return(entornodedatos.ListaCategoria.get(i));
    }

    @Override
    public long getItemId(int i)
    {
        return (entornodedatos.ListaCategoria.get(i).getCategoriaPK());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View Vista;

        LayoutInflater Inflador = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        Vista = Inflador.inflate(R.layout.categoriaitem,null);

        TextView CategoriaItem_TextView = Vista.findViewById(R.id.CategoriaItem_TextView);

        CategoriaItem_TextView.setText(entornodedatos.ListaCategoria.get(i).toString());

        return Vista;
    }
}
