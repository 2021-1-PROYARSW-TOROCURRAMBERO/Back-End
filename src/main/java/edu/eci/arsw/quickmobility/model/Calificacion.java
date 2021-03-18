package edu.eci.arsw.quickmobility.model;

import org.springframework.data.annotation.Id;

public class Calificacion {
    @Id
    public String id;
    public int valor;


    public Calificacion(){

    }
    public Calificacion( int valor) {
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
