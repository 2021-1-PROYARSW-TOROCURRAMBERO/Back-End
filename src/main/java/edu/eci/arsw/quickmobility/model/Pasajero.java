package edu.eci.arsw.quickmobility.model;

import org.springframework.data.annotation.Id;

public class Pasajero {
    @Id
    public String id;
    public String username;
    public String direccionRecogida;
    public Estado estado;
    public Conductor conductor;
    public Calificacion calificacion;

    public Pasajero(){

    }

    public Pasajero(String direccionRecogida, Estado estado, Conductor conductor, Calificacion calificacion){
        this.direccionRecogida = direccionRecogida;
        this.estado = estado;
        this.conductor = conductor;
        this.calificacion = calificacion;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDireccionRecogida() {
        return direccionRecogida;
    }

    public void setDireccionRecogida(String direccionRecogida) {
        this.direccionRecogida = direccionRecogida;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }

    @Override
    public String toString() {
        return "Pasajero{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", direccionRecogida='" + direccionRecogida + '\'' +
                ", estado=" + estado +
                ", conductor=" + conductor +
                ", calificacion=" + calificacion +
                '}';
    }
}
