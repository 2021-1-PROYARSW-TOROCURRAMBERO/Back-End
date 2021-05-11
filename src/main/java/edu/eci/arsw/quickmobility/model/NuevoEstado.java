package edu.eci.arsw.quickmobility.model;

public class NuevoEstado {
    public String conductorUsername;
    public boolean estado;
    public NuevoEstado(){}

    public NuevoEstado(String conductorUsername, boolean estado) {
        this.conductorUsername = conductorUsername;
        this.estado = estado;
    }

    public String getConductorUsername() {
        return conductorUsername;
    }

    public void setConductorUsername(String conductorUsername) {
        this.conductorUsername = conductorUsername;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "NuevoEstado{" +
                "conductorUsername='" + conductorUsername + '\'' +
                ", estado=" + estado +
                '}';
    }
}
