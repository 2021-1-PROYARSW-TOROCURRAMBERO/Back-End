package edu.eci.arsw.quickmobility.model;

public class InformacionPasajero {
    public String pasajeroUsername,direccion;
    public InformacionPasajero(){}
    public InformacionPasajero(String pasajeroUsername, String direccion) {
        this.pasajeroUsername = pasajeroUsername;
        this.direccion = direccion;
    }

    public String getPasajeroUsername() {
        return pasajeroUsername;
    }

    public void setPasajeroUsername(String pasajeroUsername) {
        this.pasajeroUsername = pasajeroUsername;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "InformacionPasajero{" +
                "pasajeroUsername='" + pasajeroUsername + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
