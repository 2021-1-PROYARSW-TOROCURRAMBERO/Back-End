package edu.eci.arsw.quickmobility.model;

import org.springframework.data.annotation.Id;

public class Calificacion {
    @Id
    public String id;
    public double valor;


    public Calificacion(){

    }
    public Calificacion( double valor) {
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Calificacion{" +
                "id='" + id + '\'' +
                ", valor=" + valor +
                '}';
    }
}
