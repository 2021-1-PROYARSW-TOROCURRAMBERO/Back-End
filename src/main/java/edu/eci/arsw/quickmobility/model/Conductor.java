package edu.eci.arsw.quickmobility.model;

import org.springframework.data.annotation.Id;

public class Conductor {

    @Id
    public String id;
    public String precio, direccionInicio, direccionFin, horaInicio, horaFin,estado;
    public Calificacion calificacion;
    public Carro carro;

    public Conductor() {
    }

    public Conductor(String id, String precio, String direccionInicio, String direccionFin,
                     String horaInicio, String horaFin, String estado, Calificacion calificacion, Carro carro) {
        this.id = id;
        this.precio = precio;
        this.direccionInicio = direccionInicio;
        this.direccionFin = direccionFin;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
        this.calificacion = calificacion;
        this.carro = carro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDireccionInicio() {
        return direccionInicio;
    }

    public void setDireccionInicio(String direccionInicio) {
        this.direccionInicio = direccionInicio;
    }

    public String getDireccionFin() {
        return direccionFin;
    }

    public void setDireccionFin(String direccionFin) {
        this.direccionFin = direccionFin;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
