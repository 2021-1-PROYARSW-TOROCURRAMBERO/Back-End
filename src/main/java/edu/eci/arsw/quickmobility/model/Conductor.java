package edu.eci.arsw.quickmobility.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Conductor {

    @Id
    public String id;
    public String precio, horaInicio, horaFin,username;
    public ArrayList<String> direccionInicio, direccionFin;
    public Calificacion calificacion;
    public Estado estado;
    public Carro carro;
    public List<Pasajero> posiblesPasajeros;

    public Conductor() {
    }

    public Conductor(String id, String precio, ArrayList<String> direccionInicio, ArrayList<String> direccionFin,
                     String horaInicio, String horaFin, Estado estado, Calificacion calificacion, Carro carro, List<Pasajero> posiblesPasajeros) {
        this.id = id;
        this.precio = precio;
        this.direccionInicio = direccionInicio;
        this.direccionFin = direccionFin;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
        this.calificacion = calificacion;
        this.posiblesPasajeros = posiblesPasajeros;
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

    public ArrayList<String> getDireccionInicio() {
        return direccionInicio;
    }

    public void setDireccionInicio(ArrayList<String> direccionInicio) {
        this.direccionInicio = direccionInicio;
    }

    public ArrayList<String> getDireccionFin() {
        return direccionFin;
    }

    public void setDireccionFin(ArrayList<String> direccionFin) {
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public List<Pasajero> getPosiblesPasajeros() {
        return posiblesPasajeros;
    }

    public void setPosiblesPasajeros(List<Pasajero> posiblesPasajeros) {
        this.posiblesPasajeros = posiblesPasajeros;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Conductor{" +
                "id='" + id + '\'' +
                ", precio='" + precio + '\'' +
                ", direccionInicio='" + direccionInicio + '\'' +
                ", direccionFin='" + direccionFin + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFin='" + horaFin + '\'' +
                ", username='" + username + '\'' +
                ", calificacion=" + calificacion +
                ", estado=" + estado +
                ", carro=" + carro +
                ", posiblesPasajeros=" + posiblesPasajeros +
                '}';
    }
}
