package com.example.tpfinal_grupal_mobil.entidades.categoria;

public class Categoria
{
    Long CategoriaPK;
    String CategoriaNombre;
    Long IDDistribuidora;



    public Categoria(Long categoriaPK, String categoriaNombre, Long IDDistribuidora)
        {
            CategoriaPK = categoriaPK;
            CategoriaNombre = categoriaNombre;
            IDDistribuidora= IDDistribuidora;
        }

    public Categoria()
        {
            CategoriaPK = Long.valueOf(0);
            CategoriaNombre = "";
            IDDistribuidora = Long.valueOf(0);
        }

        public Long getCategoriaPK()
        {
            return CategoriaPK;
        }

        public void setCategoriaPK(Long categoriaPK)
        {
            CategoriaPK = categoriaPK;
        }

        public String getCategoriaNombre()
        {
            return CategoriaNombre;
        }

        public void setCategoriaNombre(String categoriaNombre)
        {
            CategoriaNombre = categoriaNombre;
        }
        public  Long getIDDistribuidora()
        {
            return IDDistribuidora;
        }

        public void setIDDistribuidora(Long IDDistribuidora)
        {
            IDDistribuidora = IDDistribuidora;
        }

        @Override
        public String toString()
        {
            return   "Categoria ID : "+CategoriaPK + " - " + " Categoria Nombre : " + CategoriaNombre+ " - " +" Distribuidora ID : "+IDDistribuidora;
        }
}


