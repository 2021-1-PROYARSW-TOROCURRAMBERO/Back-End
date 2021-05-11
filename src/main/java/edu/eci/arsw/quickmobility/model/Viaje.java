package edu.eci.arsw.quickmobility.model;

import java.util.ArrayList;

public class Viaje {
    private String precio;
    private ArrayList<String> origen;
    private ArrayList<String> destino;
    private String carro;

    public Viaje(){
    }

    public Viaje(String precio, ArrayList<String> origen, ArrayList<String> destino, String carro) {
        this.precio = precio;
        this.origen = origen;
        this.destino = destino;
        this.carro = carro;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public ArrayList<String> getOrigen() {
        return origen;
    }

    public void setOrigen(ArrayList<String> origen) {
        this.origen = origen;
    }

    public ArrayList<String> getDestino() {
        return destino;
    }

    public void setDestino(ArrayList<String> destino) {
        this.destino = destino;
    }

    public String getCarro() {
        return carro;
    }

    public void setCarro(String carro) {
        this.carro = carro;
    }
}
