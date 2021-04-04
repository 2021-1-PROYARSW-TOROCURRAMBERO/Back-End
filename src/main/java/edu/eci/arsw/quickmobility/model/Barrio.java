package edu.eci.arsw.quickmobility.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "neighborhood")
public class Barrio {
    @Id
    private String id;
    private String nombre;

    public Barrio() {}

    public Barrio(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
