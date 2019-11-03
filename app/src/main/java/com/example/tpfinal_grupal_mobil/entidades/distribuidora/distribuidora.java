package com.example.tpfinal_grupal_mobil.entidades.distribuidora;

public class distribuidora
{
    Long iddistribuidora;
    String distribuidoracuit;
    String distribuidoranombre;
    String distribuidoradomicilio;

    public distribuidora()
    {

        this.iddistribuidora = Long.valueOf(0);
        this.distribuidoracuit = "";
        this.distribuidoranombre = "";
        this.distribuidoradomicilio = "";
    }

    public distribuidora(Long iddistribuidora, String distribuidoracuit, String distribuidoranombre, String distribuidoradomicilio) {
        this.iddistribuidora = iddistribuidora;
        this.distribuidoracuit = distribuidoracuit;
        this.distribuidoranombre = distribuidoranombre;
        this.distribuidoradomicilio = distribuidoradomicilio;
    }

    

    public Long getIddistribuidora() {
        return iddistribuidora;
    }

    public void setIddistribuidora(Long iddistribuidora) {
        this.iddistribuidora = iddistribuidora;
    }

    public String getDistribuidoracuit() {
        return distribuidoracuit;
    }

    public void setDistribuidoracuit(String distribuidoracuit) {
        this.distribuidoracuit = distribuidoracuit;
    }

    public String getDistribuidoranombre() {
        return distribuidoranombre;
    }

    public void setDistribuidoranombre(String distribuidoranombre) {
        this.distribuidoranombre = distribuidoranombre;
    }

    public String getDistribuidoradomicilio() {
        return distribuidoradomicilio;
    }

    public void setDistribuidoradomicilio(String distribuidoradomicilio) {
        this.distribuidoradomicilio = distribuidoradomicilio;
    }

    @Override
    public String toString() {
        return "distribuidora{" +
                "iddistribuidora=" + iddistribuidora +
                ", distribuidoracuit='" + distribuidoracuit + '\'' +
                ", distribuidoranombre='" + distribuidoranombre + '\'' +
                ", distribuidoradomicilio='" + distribuidoradomicilio + '\'' +
                '}';
    }
}
