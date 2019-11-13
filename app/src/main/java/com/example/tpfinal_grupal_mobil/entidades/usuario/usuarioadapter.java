package com.example.tpfinal_grupal_mobil.entidades.usuario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.tpfinal_grupal_mobil.R;
import com.example.tpfinal_grupal_mobil.entidades.entornodedatos;


public class usuarioadapter extends BaseAdapter {

    Context context;

    public usuarioadapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return entornodedatos.ListaUsuario.size();
    }

    @Override
    public Object getItem(int i) {
        return entornodedatos.ListaUsuario.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (entornodedatos.ListaUsuario.get(i).getIdusuario());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vista;

        LayoutInflater Inflador=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        vista = Inflador.inflate(R.layout.usuarioitem,null);

        TextView Usuario_Item_Txv = vista.findViewById(R.id.Usuario_Item_Txv);
        Usuario_Item_Txv.setText(entornodedatos.ListaUsuario.get(i).usuarionombre);



        return vista;
    }
}
