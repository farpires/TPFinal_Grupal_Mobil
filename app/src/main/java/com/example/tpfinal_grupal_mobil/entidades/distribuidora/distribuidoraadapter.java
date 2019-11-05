package com.example.tpfinal_grupal_mobil.entidades.distribuidora;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tpfinal_grupal_mobil.R;
import com.example.tpfinal_grupal_mobil.entidades.entornodedatos;

public class distribuidoraadapter extends BaseAdapter
{
    Context context;

    public distribuidoraadapter(Context context)
    {
        this.context = context;
    }

    @Override
    public int getCount() {
        return(entornodedatos.ListaDistribuidora.size());
    }

    @Override
    public Object getItem(int i) {
        return(entornodedatos.ListaDistribuidora.get(i));
    }

    @Override
    public long getItemId(int i) {
        return (entornodedatos.ListaDistribuidora.get(i).getIddistribuidora());
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View Vista;

        LayoutInflater Inflador = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        Vista = Inflador.inflate(R.layout.distribuidoraitem,null);

        TextView CategoriaItem_TextView = Vista.findViewById(R.id.DistribuidoraItem_TextView);

        CategoriaItem_TextView.setText(entornodedatos.ListaDistribuidora.get(i).toString());

        return Vista;
    }
}
